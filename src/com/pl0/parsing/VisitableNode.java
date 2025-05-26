package com.pl0.parsing;

public interface VisitableNode {
    void accept(SyntaxTreeVisitor visitor);
}