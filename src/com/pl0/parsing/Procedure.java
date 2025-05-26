package com.pl0.parsing;

import java.util.List;

public class Procedure extends Statement {
    private final Identifier identifier;
    private final List<Declaration> declarations;
    private final List<Procedure> procedures;
    private final Statement statement;

    public Procedure(Identifier identifier, List<Declaration> declarations, List<Procedure> procedures, Statement statement) {
        this.identifier = identifier;
        this.declarations = declarations;
        this.procedures = procedures;
        this.statement = statement;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitProcedure(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Procedure " + identifier + ":\n");
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
