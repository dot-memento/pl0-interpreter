package com.pl0.errorhandling;

import com.pl0.common.FilePosition;

public class ErrorBuilder {
    private String message;
    private String fileName;
    private int line = -1, column = -1;
    private Throwable throwable;

    public ErrorBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ErrorBuilder withFilePosition(int line, int column) {
        this.line = line;
        this.column = column;
        return this;
    }

    public ErrorBuilder withThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public CompilationError build() {
        FilePosition filePosition = new FilePosition(fileName, line, column);
        if (throwable != null)
            return new CompilationError(message, filePosition, throwable);
        return new CompilationError(message, filePosition);
    }
}
