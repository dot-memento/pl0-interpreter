package com.pl0.parsing;

import java.util.ArrayList;

public class CompoundStatement extends Statement {
    private final ArrayList<Statement> statements;

    public CompoundStatement(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        for (Statement statement : statements)
            statement.accept(visitor);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Statement statement : statements) {
            builder.append(statement);
            builder.append("\n");
        }
        return builder.toString();
    }
}
