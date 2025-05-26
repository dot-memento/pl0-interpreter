package com.pl0.parsing;

public class WhileStatement extends Statement {
    private final Condition condition;
    private final Statement statement;

    public WhileStatement(Condition condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitWhile(this);
    }

    public Condition getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }
    
    @Override
    public String toString() {
        return "While " + condition + " then\n" + statement;
    }
}
