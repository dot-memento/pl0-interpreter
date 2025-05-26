package com.pl0.parsing;

public interface SyntaxTreeVisitor {
    void visitDeclaration(Declaration declaration);
    void visitAssignment(AssignStatement assignStatement);
    void visitIf(IfStatement ifStatement);
    void visitWhile(WhileStatement whileStatement);
    void visitProcedure(Procedure procedure);
    void visitCall(CallStatement callStatement);
    void visitRead(ReadStatement readStatement);
    void visitWrite(WriteStatement writeStatement);
}
