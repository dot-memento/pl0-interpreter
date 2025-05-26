package com.pl0.lexing;

public class IntegerToken extends Token {
    private final int value;

    public IntegerToken(TokenEnum type, int line, int column, int offset, int value) {
        super(type, line, column, offset);
        this.value = value;
    }

    public int value() {
        return value;
    }
    
    @Override
    public String toString() {
        return getType().toString() + " : " + value;
    }
}