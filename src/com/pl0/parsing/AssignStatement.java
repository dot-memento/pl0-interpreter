package com.pl0.parsing;

import com.pl0.parsing.expression.Expression;

public class AssignStatement extends Statement {
    private final Identifier identifier;
    private final Expression expression;

    public AssignStatement(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitAssignment(this);
    }
    
    @Override
    public String toString() {
        return "Assign " + expression + " to " + identifier;
    }
}
