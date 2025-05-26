package com.pl0.parsing;

public class Identifier {
    private final String symbol;

    public Identifier(String symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public String toString() {
        return symbol;
    }

    public String name() {
        return symbol;
    }
}
