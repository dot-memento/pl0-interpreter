package com.pl0.parsing;

public class ReadStatement extends Statement {
    private final Identifier identifier;

    public ReadStatement(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitRead(this);
    }
    
    @Override
    public String toString() {
        return "Read " + identifier;
    }
}
