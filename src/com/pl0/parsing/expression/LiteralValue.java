package com.pl0.parsing.expression;

import com.pl0.interpreting.Interpreter;

public class LiteralValue implements Expression {
    private final int value;

    public LiteralValue(int value) {
        this.value = value;
    }

    @Override
    public int evaluateValue(Interpreter evaluator) {
        return value;
    }
        
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
