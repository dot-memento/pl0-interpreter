package com.pl0;

import com.pl0.common.Compiler;
import com.pl0.interpreting.Interpreter;
import com.pl0.parsing.SyntaxTree;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No input file");
            return;
        }

        String fileName = args[0];
        Compiler compiler = new Compiler();
        SyntaxTree ast = compiler.generateSyntaxTree(fileName);

        if (ast == null)
            return;

        Interpreter interpreter = new Interpreter();

        ast.accept(interpreter);
    }
}
