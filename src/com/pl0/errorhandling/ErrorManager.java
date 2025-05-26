package com.pl0.errorhandling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorManager {
    private final List<CompilationError> errorList = new ArrayList<>();

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }

    public List<CompilationError> getErrorList() {
        return Collections.unmodifiableList(errorList);
    }

    public void addError(CompilationError error) {
        errorList.add(error);
    }

    public void clearErrors() {
        errorList.clear();
    }
}
