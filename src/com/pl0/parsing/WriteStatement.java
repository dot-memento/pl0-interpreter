package com.pl0.parsing;

import com.pl0.parsing.expression.Expression;

public class WriteStatement extends Statement {
    private final Expression expression;

    public WriteStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitWrite(this);
    }
    
    @Override
    public String toString() {
        return "Write " + expression;
    }
}
