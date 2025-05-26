package com.pl0.parsing.expression;

import com.pl0.interpreting.Interpreter;

public class UnaryOperation implements Expression {
    public enum Operator {
        NOTHING, OPPOSITE
    }

    private final Operator operator;
    private final Expression expr;
    
    public UnaryOperation(Operator operator, Expression expr) {
        this.operator = operator;
        this.expr = expr;
    }
    
    @Override
    public int evaluateValue(Interpreter evaluator) {
        int value = expr.evaluateValue(evaluator);
        return operator == Operator.OPPOSITE ? -value : value;
    }
        
    @Override
    public String toString() {
        return operator + " " + expr;
    }
}
