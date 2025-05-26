package com.pl0.lexing;

public class LexerReader {
    private final Lexer lexer;
    private Token lastRead = null;
    private Token nextToken;

    public LexerReader(Lexer lexer) {
        this.lexer = lexer;
        nextToken = lexer.getNextToken();
    }

    public Token peekNext() {
        return nextToken;
    }

    public TokenEnum peekNextType() {
        return nextToken.getType();
    }
    
    public boolean isNext(TokenEnum type) {
        return nextToken.getType() == type;
    }

    public Token getLastRead() {
        return lastRead;
    }

    public Token getNext() {
        lastRead = nextToken;
        nextToken = lexer.getNextToken();
        return lastRead;
    }
}
