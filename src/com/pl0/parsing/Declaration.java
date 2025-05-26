package com.pl0.parsing;

import com.pl0.parsing.expression.LiteralValue;

public class Declaration implements VisitableNode {
    private final Identifier identifier;
    private final LiteralValue value;

    public Declaration(Identifier identifier, LiteralValue value) {
        this.identifier = identifier;
        this.value = value;
    }

    public Declaration(Identifier identifier) {
        this.identifier = identifier;
        this.value = null;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public LiteralValue getValue() {
        return value;
    }

    public boolean isConst() {
        return value != null;
    }

    @Override
    public String toString() {
        return value != null ? "Declare const " + identifier + " = " + value : "Declare var " + identifier;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitDeclaration(this);
    }
}
