package com.pl0.errorhandling;

import com.pl0.common.FilePosition;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CompilationError {
    private final String message;
    private final FilePosition position;
    private final Throwable throwable;

    public CompilationError(String message, FilePosition position) {
        this.message = message;
        this.position = position;
        this.throwable = null;
    }

    public CompilationError(String message, FilePosition position, Throwable throwable) {
        this.message = message;
        this.position = position;
        this.throwable = throwable;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (position.getFileName() != null)
            builder.append(position.getFileName()).append(':');
        if (position.getLine() >= 0)
        {
            builder.append(position.getLine()).append(':');
            if (position.getColumn() >= 0)
                builder.append(position.getColumn()).append(':');
        }

        if (message != null)
            builder.append(' ').append(message);

        if (throwable != null) {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            builder.append('\n').append(writer);
        }

        return builder.toString();
    }
}
