package com.pl0.lexing;

import com.pl0.common.SourceCodeSupplier;
import com.pl0.errorhandling.ErrorBuilder;

public class Lexer {
    private final SourceCodeSupplier codeSupplier;
    private final String code;
    private final int codeLength;
    private int currentPosition = 0;
    private int currentLine = 1, lineStartOffset = 0;


    public Lexer(SourceCodeSupplier codeSupplier) {
        this.codeSupplier = codeSupplier;
        this.code = codeSupplier.getContent();
        this.codeLength = this.code != null ? code.length() : 0;
    }


    public Token getNextToken() {
        char character;
        while ((character = consume()) != 0) {
            if (Character.isLetter(character))
                return identifier(character);

            if (Character.isDigit(character))
                return number(character);

            switch (character) {
                case '(': return simple(TokenEnum.LPAREN, 1);
                case ')': return simple(TokenEnum.RPAREN, 1);
                case '*': return simple(TokenEnum.TIMES, 1);
                case '/': return simple(TokenEnum.SLASH, 1);
                case '+': return simple(TokenEnum.PLUS, 1);
                case '-': return simple(TokenEnum.MINUS, 1);
                case ';': return simple(TokenEnum.SEMICOLON, 1);
                case ',': return simple(TokenEnum.COMMA, 1);
                case '.': return simple(TokenEnum.PERIOD, 1);
                case ':':
                    if (peek() != '=')
                        error("Expected '='");
                    else
                        skip();
                    return simple(TokenEnum.BECOMES, 2);

                case '=': return simple(TokenEnum.EQL, 1);
                case '#': return simple(TokenEnum.NEQ, 1);
                case '<':
                    if (peek() == '=') {
                        skip();
                        return simple(TokenEnum.LEQ, 2);
                    }
                    return simple(TokenEnum.LSS, 1);
                case '>':
                    if (peek() == '=') {
                        skip();
                        return simple(TokenEnum.GEQ, 2);
                    }
                    return simple(TokenEnum.GTR, 1);

                case '!': return simple(TokenEnum.WRITE, 1);
                case '?': return simple(TokenEnum.READ, 1);

                case '\n':
                    currentLine++;
                    lineStartOffset = currentPosition;
                    break;
            }
        }

        return simple(TokenEnum.EOF, 0);
    }

    private Token simple(TokenEnum type, int length) {
        return new Token(type, currentLine, getColumn() - length, currentPosition - length);
    }

    private Token identifier(char startingCharacter) {
        int startingColumn = getColumn();

        StringBuilder builder = new StringBuilder().append(startingCharacter);
        while (Character.isAlphabetic(peek()))
            builder.append(consume());
        String string = builder.toString();

        String lowercaseString = string.toLowerCase();
        TokenEnum tokenType = TokenEnum.valueOfSymbol(lowercaseString);
        if (tokenType != null)
            return simple(tokenType, lowercaseString.length());

        return new StringToken(TokenEnum.IDENT,
                currentLine, startingColumn,
                currentPosition - lowercaseString.length(), string);
    }

    private Token number(char startingCharacter) {
        int column = getColumn();

        StringBuilder builder = new StringBuilder().append(startingCharacter);
        while (Character.isDigit(peek()))
            builder.append(consume());

        String intString = builder.toString();
        return new IntegerToken(TokenEnum.NUMBER, currentLine,
                column, currentPosition - intString.length(),
                Integer.parseInt(intString));
    }
    

    private char peek() {
        return currentPosition < codeLength ? code.charAt(currentPosition) : 0;
    }
    
    private void skip() {
        if (currentPosition < codeLength)
            currentPosition += 1;
    }
    
    private char consume() {
        char character = peek();
        skip();
        return character;
    }

    private int getColumn() {
        return currentPosition - lineStartOffset + 1;
    }

    private void error(String message) {
        codeSupplier.handleError(new ErrorBuilder()
            .withMessage(message)
            .withFilePosition(currentLine, currentPosition));
    }
}
