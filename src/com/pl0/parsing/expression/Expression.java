package com.pl0.parsing.expression;

import com.pl0.interpreting.Interpreter;

public interface Expression {
    int evaluateValue(Interpreter evaluator);
}
