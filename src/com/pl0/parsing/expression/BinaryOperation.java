package com.pl0.parsing.expression;

import com.pl0.interpreting.Interpreter;

public class BinaryOperation implements Expression {
    public enum Operator {
        ADD, SUBSTRACT,
        MULTIPLY, DIVIDE
    }

    private final Operator operator;
    private final Expression lexpr, rexpr;
    
    public BinaryOperation(Expression lexpr, Operator operator, Expression rexpr) {
        this.operator = operator;
        this.lexpr = lexpr;
        this.rexpr = rexpr;
    }
    
    @Override
    public int evaluateValue(Interpreter evaluator) {
        int lvalue = lexpr.evaluateValue(evaluator);
        int rvalue = rexpr.evaluateValue(evaluator);
        return switch (operator) {
            case ADD        -> lvalue + rvalue;
            case SUBSTRACT  -> lvalue - rvalue;
            case MULTIPLY   -> lvalue * rvalue;
            case DIVIDE     -> lvalue / rvalue;
        };
    }
    
    @Override
    public String toString() {
        return lexpr + " " + operator + " " + rexpr;
    }
}
