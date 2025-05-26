package com.pl0.parsing.expression;

import com.pl0.interpreting.Interpreter;
import com.pl0.parsing.Identifier;

public class VariableValue implements Expression {
    private final Identifier identifier;

    public VariableValue(Identifier identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public int evaluateValue(Interpreter evaluator) {
        return evaluator.getVariableValue(identifier);
    }
    
    @Override
    public String toString() {
        return identifier.toString();
    }
}
