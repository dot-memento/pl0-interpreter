package com.pl0.lexing;

public class StringToken extends Token {
    private final String string;

    public StringToken(TokenEnum type, int line, int column, int offset, String string) {
        super(type, line, column, offset);
        this.string = string;
    }

    public String string() {
        return string;
    }
    
    @Override
    public String toString() {
        return getType().toString() + " : " + string;
    }
}