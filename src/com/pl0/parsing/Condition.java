package com.pl0.parsing;

import com.pl0.interpreting.Interpreter;
import com.pl0.lexing.TokenEnum;
import com.pl0.parsing.expression.Expression;

public class Condition implements Expression {
    private final TokenEnum type;
    private final Expression lexpr;
    private final Expression rexpr;

    public Condition(TokenEnum type, Expression lexpr, Expression rexpr) {
        this.type = type;
        this.lexpr = lexpr;
        this.rexpr = rexpr;
    }

    @Override
    public int evaluateValue(Interpreter evaluator) {
        int lvalue = lexpr.evaluateValue(evaluator);
        int rvalue = rexpr.evaluateValue(evaluator);
        return switch (type) {
            case EQL -> lvalue == rvalue;
            case NEQ -> lvalue != rvalue;
            case LSS -> lvalue <  rvalue;
            case LEQ -> lvalue <= rvalue;
            case GTR -> lvalue >  rvalue;
            case GEQ -> lvalue >= rvalue;
            default -> false;
        } ? 1 : 0;
    }

    @Override
    public String toString() {
        return lexpr + " " + type + " " + rexpr;
    }
}
