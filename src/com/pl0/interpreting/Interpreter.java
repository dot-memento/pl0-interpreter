package com.pl0.interpreting;

import com.pl0.parsing.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Interpreter implements SyntaxTreeVisitor {
    private final Stack<HashMap<String, Symbol>> symbolTables = new Stack<>();

    public Interpreter() {
        symbolTables.push(new HashMap<>());
    }

    public Symbol findSymbol(String name) {
        Symbol symbol = null;
        for (HashMap<String, Symbol> symbolTable : symbolTables)
            if ((symbol = symbolTable.get(name)) != null)
                break;
        return symbol;
    }

    public int getVariableValue(Identifier identifier) {
        Symbol symbol = findSymbol(identifier.name());
        if (symbol == null)
            throw new RuntimeException("Undeclared variable '" + identifier.name() + "'");
        if (symbol.getClass() != IntegerSymbol.class)
            throw new RuntimeException("'" + identifier.name() + "' is not a number");

        return ((IntegerSymbol) symbol).getValue();
    }

    @Override
    public void visitDeclaration(Declaration declaration) {
        String symbolString = declaration.getIdentifier().name();
        if (findSymbol(symbolString) != null)
            throw new RuntimeException("Cannot redeclare variable '" + symbolString + "'");

        if (declaration.isConst())
            symbolTables.peek().put(symbolString, new IntegerSymbol(declaration.getValue().evaluateValue(this)));
        else
            symbolTables.peek().put(symbolString, new IntegerSymbol());
    }

    @Override
    public void visitAssignment(AssignStatement assignStatement) {
        Identifier identifier = assignStatement.getIdentifier();
        int value = assignStatement.getExpression().evaluateValue(this);
        assignVariable(identifier, value);
    }

    public void assignVariable(Identifier identifier, int value) {
        String symbolString = identifier.name();
        Symbol symbol = findSymbol(symbolString);
        if (symbol == null)
            throw new RuntimeException("Undeclared variable '" + symbolString + "'");

        ((IntegerSymbol) symbol).assign(value);
    }

    @Override
    public void visitIf(IfStatement ifStatement) {
        if (ifStatement.getCondition().evaluateValue(this) != 0)
            ifStatement.getStatement().accept(this);
    }

    @Override
    public void visitWhile(WhileStatement whileStatement) {
        while (whileStatement.getCondition().evaluateValue(this) != 0)
            whileStatement.getStatement().accept(this);
    }

    @Override
    public void visitProcedure(Procedure procedure) {
        String procName = procedure.getIdentifier().name();
        Symbol symbol = findSymbol(procName);
        if (symbol != null) {
            if (symbol.getClass() == ProcedureSymbol.class)
                throw new RuntimeException("Cannot redeclare procedure '" + procName + "'");
            else
                throw new RuntimeException("Symbol '" + procName + "' already used");
        }
        symbolTables.peek().put(procName, new ProcedureSymbol(procedure));
    }

    @Override
    public void visitCall(CallStatement callStatement) {
        String procName = callStatement.getIdentifier().name();
        Symbol symbol = findSymbol(procName);
        if (symbol == null)
            throw new RuntimeException("Undeclared procedure '" + procName + "'");
        if (symbol.getClass() != ProcedureSymbol.class)
            throw new RuntimeException("'" + procName + "' is not a procedure");
        Procedure procedure = ((ProcedureSymbol) symbol).getProcedure();

        symbolTables.push(new HashMap<>());
        for (Declaration declaration : procedure.getDeclarations())
            declaration.accept(this);
        procedure.getStatement().accept(this);
        symbolTables.pop();
    }

    @Override
    public void visitWrite(WriteStatement writeStatement) {
        System.out.println(writeStatement.getExpression().evaluateValue(this));
    }

    @Override
    public void visitRead(ReadStatement readStatement) {
        Scanner in = new Scanner(System.in);
        int value = in.nextInt();
        Identifier identifier = readStatement.getIdentifier();
        assignVariable(identifier, value);
    }
}
