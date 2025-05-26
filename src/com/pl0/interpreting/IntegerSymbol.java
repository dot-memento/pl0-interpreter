package com.pl0.interpreting;

public class IntegerSymbol extends Symbol {
    private int value;
    private boolean isInitialized;
    private final boolean isConst;

    public IntegerSymbol(int value) {
        this.value = value;
        this.isConst = true;
        this.isInitialized = true;
    }

    public IntegerSymbol() {
        this.isConst = false;
        this.isInitialized = false;
    }

    public void assign(int value) {
        if (isConst)
            throw new RuntimeException("Variable is declared const");
        this.value = value;
        this.isInitialized = true;
    }

    public int getValue() {
        if (!isInitialized)
            throw new RuntimeException("Uninitialized variable");
        return value;
    }
}
