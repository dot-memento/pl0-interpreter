package com.pl0.parsing;

import com.pl0.common.SourceCodeSupplier;
import com.pl0.errorhandling.ErrorBuilder;
import com.pl0.lexing.*;
import com.pl0.parsing.expression.BinaryOperation;
import com.pl0.parsing.expression.Expression;
import com.pl0.parsing.expression.LiteralValue;
import com.pl0.parsing.expression.UnaryOperation;
import com.pl0.parsing.expression.VariableValue;
import java.util.*;

public class Parser {

    private final LexerReader lexer;
    private final SourceCodeSupplier codeSupplier;

    public Parser(LexerReader lexer, SourceCodeSupplier codeSupplier) {
        this.lexer = lexer;
        this.codeSupplier = codeSupplier;
    }

    public SyntaxTree generateSyntaxTree() throws IllegalStateException {
        if (lexer.isNext(TokenEnum.EOF))
            return null;

        List<Declaration> globalDeclarations = declarations();
        List<Procedure> procedures = new ArrayList<>();
        while (lexer.isNext(TokenEnum.PROCEDURE))
            procedures.add(procedure());
        Statement mainStatement = statement();
        if (!expect(TokenEnum.PERIOD))
            error("Missing '.'", lexer.getLastRead());

        return new SyntaxTree(globalDeclarations, procedures, mainStatement);
    }

    private Procedure procedure() {
        if (!expect(TokenEnum.PROCEDURE))
            error("Excepted 'procedure'", lexer.getLastRead());
        Identifier procIdentifier = identifier();
        if (!expect(TokenEnum.SEMICOLON))
            error("Missing ';'", lexer.getLastRead());
        List<Declaration> declarations = declarations();
        List<Procedure> procedures = new ArrayList<>();
        while (lexer.isNext(TokenEnum.PROCEDURE))
            procedures.add(procedure());
        Statement statement = statement();
        if (!expect(TokenEnum.SEMICOLON))
            error("Missing ';'", lexer.getLastRead());
        return new Procedure(procIdentifier, declarations, procedures, statement);
    }

    private List<Declaration> declarations() {
        ArrayList<Declaration> declarationList = new ArrayList<>();
        constDeclarations(declarationList);
        varDeclarations(declarationList);
        declarationList.trimToSize();
        return declarationList;
    }

    private void constDeclarations(List<Declaration> declarationList) {
        if (!lexer.isNext(TokenEnum.CONST))
            return;
        do {
            lexer.getNext();

            Identifier identifier = identifier();
            if (identifier == null) {
                if (resyncConstDeclarations()) continue; else return;
            }

            if (!expect(TokenEnum.EQL)) {
                error("Expected '='", lexer.getLastRead());
                if (resyncConstDeclarations()) continue; else return;
            }

            LiteralValue value = number();
            if (value == null) {
                if (resyncConstDeclarations()) continue; else return;
            }

            declarationList.add(new Declaration(identifier, value));
        } while (lexer.isNext(TokenEnum.COMMA));

        if (expect(TokenEnum.SEMICOLON))
            return;
        error("Missing ';'", lexer.getLastRead());
    }

    private boolean resyncConstDeclarations() {
        if (lexer.getLastRead().getType() == TokenEnum.SEMICOLON)
            return false;
        while (!lexer.isNext(TokenEnum.EOF)) {
            lexer.getNext();
            if (lexer.isNext(TokenEnum.COMMA))
                return true;
            if (lexer.isNext(TokenEnum.SEMICOLON)) {
                lexer.getNext();
                return false;
            }
        }
        return false;
    }

    private void varDeclarations(List<Declaration> declarationList) {
        if (!lexer.isNext(TokenEnum.VAR))
            return;

        do {
            lexer.getNext();
            Identifier identifier = identifier();
            if (identifier == null) {
                if (resyncVarDeclarations()) continue; else return;
            }

            declarationList.add(new Declaration(identifier));
        } while (lexer.isNext(TokenEnum.COMMA));

        if (expect(TokenEnum.SEMICOLON))
            return;
        error("Missing ';'", lexer.getLastRead());
    }

    private boolean resyncVarDeclarations() {
        if (lexer.getLastRead().getType() == TokenEnum.SEMICOLON)
            return false;
        while (!lexer.isNext(TokenEnum.EOF)) {
            lexer.getNext();
            if (lexer.isNext(TokenEnum.COMMA))
                return true;
            if (lexer.isNext(TokenEnum.SEMICOLON)) {
                lexer.getNext();
                return false;
            }
        }
        return false;
    }

    private Statement statement() {
        switch (lexer.peekNextType()) {
            case IDENT:
                Identifier varIdentifier = identifier();
                if (!expect(TokenEnum.BECOMES)) {
                    error("Missing ':='", lexer.getLastRead());
                    return null;
                }
                Expression expr = expression();
                return new AssignStatement(varIdentifier, expr);

            case IF:
                lexer.getNext();
                Condition ifCondition = condition();
                if (!expect(TokenEnum.THEN)) {
                    error("Missing 'then'", lexer.getLastRead());
                    return null;
                }
                Statement ifStatement = statement();
                return new IfStatement(ifCondition, ifStatement);

            case WHILE:
                lexer.getNext();
                Condition whileCondition = condition();
                if (!expect(TokenEnum.DO)) {
                    error("Missing 'do'", lexer.getLastRead());
                    return null;
                }
                Statement whileStatement = statement();
                return new WhileStatement(whileCondition, whileStatement);

            case BEGIN:
                ArrayList<Statement> statementList = new ArrayList<>();

                do {
                    do {
                        lexer.getNext();
                        statementList.add(statement());
                    } while (lexer.isNext(TokenEnum.SEMICOLON));
                    if (!lexer.isNext(TokenEnum.END))
                        error("Missing ';'", lexer.getLastRead());
                } while (!lexer.isNext(TokenEnum.END) && !lexer.isNext(TokenEnum.EOF));
                lexer.getNext();

                statementList.trimToSize();
                return new CompoundStatement(statementList);

            case CALL:
                lexer.getNext();
                Identifier procIndentifier = identifier();
                if (procIndentifier == null)
                    return null;
                return new CallStatement(procIndentifier);

            case WRITE:
                lexer.getNext();
                Expression writeExpression = expression();
                return new WriteStatement(writeExpression);

            case READ:
                lexer.getNext();
                Identifier readIdentifier = identifier();
                return new ReadStatement(readIdentifier);

            default:
                error("Expected statement", lexer.getNext());
        }

        if (lexer.getLastRead().getType() == TokenEnum.SEMICOLON || lexer.getLastRead().getType() == TokenEnum.END)
            return null;
        while (!lexer.isNext(TokenEnum.EOF) && !lexer.isNext(TokenEnum.SEMICOLON) && !lexer.isNext(TokenEnum.END))
            lexer.getNext();
        return null;
    }

    private Condition condition() {
        Expression lexpr = expression();
        if (lexpr == null)
            return null;

        TokenEnum tokenType = lexer.getNext().getType();
        if (tokenType != TokenEnum.EQL && tokenType != TokenEnum.NEQ && tokenType != TokenEnum.LSS &&
            tokenType != TokenEnum.LEQ && tokenType != TokenEnum.GTR && tokenType != TokenEnum.GEQ) {
            error("Expected comparison operator", lexer.getLastRead());
            return null;
        }

        Expression rexpr = expression();
        if (rexpr == null)
            return null;

        return new Condition(tokenType, lexpr, rexpr);
    }

    private Expression expression() {
        boolean isMinus = (lexer.isNext(TokenEnum.PLUS) || lexer.isNext(TokenEnum.MINUS))
                        && lexer.getNext().getType() == TokenEnum.MINUS;

        Expression expr = term();
        if (expr == null)
            return null;
        if (isMinus)
            expr = new UnaryOperation(UnaryOperation.Operator.OPPOSITE, expr);

        while ((lexer.isNext(TokenEnum.PLUS) || lexer.isNext(TokenEnum.MINUS))) {
            BinaryOperation.Operator operator = lexer.getNext().getType() == TokenEnum.PLUS
                    ? BinaryOperation.Operator.ADD
                    : BinaryOperation.Operator.SUBSTRACT;
            Expression nextTerm = term();
            if (nextTerm == null)
                return null;
            expr = new BinaryOperation(expr, operator, nextTerm);
        }

        return expr;
    }

    private Expression term() {
        Expression expr = factor();
        while ((lexer.isNext(TokenEnum.TIMES) || lexer.isNext(TokenEnum.SLASH))) {
            BinaryOperation.Operator operator = lexer.getNext().getType() == TokenEnum.TIMES ? BinaryOperation.Operator.MULTIPLY : BinaryOperation.Operator.DIVIDE;
            Expression nextFactor = factor();
            if (nextFactor == null)
                return null;
            expr = new BinaryOperation(expr, operator, nextFactor);
        }
        return expr;
    }

    private Expression factor() {
        switch (lexer.getNext().getType()) {
            case IDENT -> {
                return new VariableValue(new Identifier(((StringToken) lexer.getLastRead()).string()));
            }
            case NUMBER -> {
                return new LiteralValue(((IntegerToken) lexer.getLastRead()).value());
            }
            case LPAREN -> {
                Expression expr = expression();
                if (expect(TokenEnum.RPAREN))
                    return expr;
                error("')' expected", lexer.getLastRead());
                return null;
            }
            default -> {
                error("Invalid expression", lexer.getLastRead());
                return null;
            }
        }
    }

    private Identifier identifier() {
        if (expect(TokenEnum.IDENT))
            return new Identifier(((StringToken) lexer.getLastRead()).string());
        error("Identifier expected", lexer.getLastRead());
        return null;
    }

    private LiteralValue number() {
        if (expect(TokenEnum.NUMBER))
            return new LiteralValue(((IntegerToken) lexer.getLastRead()).value());
        error("Literal expected", lexer.getLastRead());
        return null;
    }

    private boolean expect(TokenEnum expectedToken) {
        return lexer.getNext().getType() == expectedToken;
    }

    private void error(String message, Token token) {
        codeSupplier.handleError(new ErrorBuilder()
                .withMessage(message)
                .withFilePosition(token.getLine(), token.getColumn()));
    }
}
