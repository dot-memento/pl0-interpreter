package com.pl0.parsing;

import java.util.List;

public class SyntaxTree implements VisitableNode {
    private final List<Declaration> declarations;
    private final List<Procedure> procedures;
    private final Statement statement;

    public SyntaxTree(List<Declaration> declarations, List<Procedure> procedures, Statement statement) {
        this.declarations = declarations;
        this.procedures = procedures;
        this.statement = statement;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        for (Declaration declaration : declarations)
            declaration.accept(visitor);
        for (Procedure procedure : procedures)
            procedure.accept(visitor);
        statement.accept(visitor);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Declaration declaration : declarations) {
            builder.append(declaration);
            builder.append("\n");
        }
        for (Procedure procedure : procedures) {
            builder.append(procedure);
            builder.append("\n");
        }
        builder.append(statement);
        return builder.toString();
    }
}
