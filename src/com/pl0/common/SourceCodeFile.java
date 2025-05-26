package com.pl0.common;

import com.pl0.errorhandling.ErrorBuilder;
import com.pl0.errorhandling.ErrorManager;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourceCodeFile implements SourceCodeSupplier {
    private final String path;
    private final ErrorManager errorManager;

    public SourceCodeFile(String path, ErrorManager errorManager) {
        this.path = path;
        this.errorManager = errorManager;
    }

    public String getPath() {
        return path;
    }

    @Override
    public void handleError(ErrorBuilder errorBuilder) {
        errorManager.addError(errorBuilder.withFileName(path).build());
    }

    @Override
    public String getContent() {
        try {
            return Files.readString(Path.of(path));
        } catch (Exception e) {
            handleError(new ErrorBuilder().withMessage("Cannot read source file").withThrowable(e));
            return null;
        }
    }
}
