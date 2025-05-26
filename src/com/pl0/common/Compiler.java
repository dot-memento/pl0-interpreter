package com.pl0.common;

import com.pl0.errorhandling.CompilationError;
import com.pl0.errorhandling.ErrorBuilder;
import com.pl0.errorhandling.ErrorManager;
import com.pl0.lexing.Lexer;
import com.pl0.lexing.LexerReader;
import com.pl0.parsing.Parser;
import com.pl0.parsing.SyntaxTree;

public class Compiler {
    private final ErrorManager errorManager = new ErrorManager();

    public Compiler() {}

    public SyntaxTree generateSyntaxTree(String fileName) {
        try {
            SourceCodeFile sourceFile = new SourceCodeFile(fileName, errorManager);
            Lexer lexer = new Lexer(sourceFile);
            Parser parser = new Parser(new LexerReader(lexer), sourceFile);
            SyntaxTree ast = parser.generateSyntaxTree();
            if (!errorManager.hasErrors())
                return ast;
        } catch (Exception e) {
            errorManager.addError(new ErrorBuilder()
                        .withMessage("Exception occurred during compilation")
                        .withFileName(fileName)
                        .withThrowable(e)
                        .build());
        }

        for (CompilationError error : errorManager.getErrorList())
            System.err.println(error.toString());

        return null;
    }
}
