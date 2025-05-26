package com.pl0.parsing;

public class IfStatement extends Statement {
    private final Condition condition;
    private final Statement statement;

    public IfStatement(Condition condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitIf(this);
    }

    public Condition getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }
    
    @Override
    public String toString() {
        return "If " + condition + " then\n" + statement;
    }
}
