package com.pl0.common;

import com.pl0.errorhandling.ErrorBuilder;

public interface SourceCodeSupplier {
    void handleError(ErrorBuilder errorBuilder);
    String getContent();
}
