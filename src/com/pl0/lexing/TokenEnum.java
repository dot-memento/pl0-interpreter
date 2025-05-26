package com.pl0.lexing;

import java.util.HashMap;
import java.util.Map;

public enum TokenEnum {
    LPAREN("("), RPAREN(")"),
    TIMES("*"), SLASH("/"), PLUS("+"), MINUS("-"),
    EQL("="), NEQ("!="),
    LSS("<"), LEQ("<="), GTR(">"), GEQ(">="),
    BECOMES(":="),
    SEMICOLON(";"),
    WRITE("!"), READ("?"),

    BEGIN("begin"), END("end"),
    IF("if"), THEN("then"),
    WHILE("while"), DO("do"),


    CONST("const"), VAR("var"), COMMA(","),
    PROCEDURE("procedure"), CALL("call"),

    PERIOD("."),
    IDENT("identifier", false),
    NUMBER("number", false),
    EOF("end of file", false);

    private static final Map<String, TokenEnum> BY_SYMBOL = new HashMap<>();

    static {
        for (TokenEnum e : values()) {
            BY_SYMBOL.put(e.string, e);
        }
    }

    public final String string;
    public final boolean isLiteral;

    TokenEnum(String string, boolean literalSymbol) {
        this.string = string;
        this.isLiteral = literalSymbol;
    }

    TokenEnum(String string) {
        this.string = string;
        this.isLiteral = true;
    }

    public static TokenEnum valueOfSymbol(String symbol) {
        return BY_SYMBOL.get(symbol);
    }
}