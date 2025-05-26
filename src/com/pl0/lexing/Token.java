package com.pl0.lexing;

public class Token {
    private final TokenEnum type;
    private final int line, column, offset;

    public Token(TokenEnum type, int line, int column, int offset) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.offset = offset;
    }

    public TokenEnum getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getOffset() {
        return offset;
    }
    
    @Override
    public String toString() {
        return type.toString();
    }
}
