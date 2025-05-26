package com.pl0.interpreting;

import com.pl0.parsing.Procedure;

public class ProcedureSymbol extends Symbol {
    private final Procedure procedure;

    public ProcedureSymbol(Procedure procedure) {
        this.procedure = procedure;
    }

    public Procedure getProcedure() {
        return procedure;
    }
}
