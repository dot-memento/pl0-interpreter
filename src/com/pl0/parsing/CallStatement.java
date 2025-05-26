package com.pl0.parsing;

public class CallStatement extends Statement {
    private final Identifier identifier;

    public CallStatement(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void accept(SyntaxTreeVisitor visitor) {
        visitor.visitCall(this);
    }
        
    @Override
    public String toString() {
        return "Call to procedure " + identifier;
    }
}
