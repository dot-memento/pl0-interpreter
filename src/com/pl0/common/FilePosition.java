package com.pl0.common;

public class FilePosition {
    private final String fileName;
    private final int line, column;

    public FilePosition(String fileName, int line, int column) {
        this.fileName = fileName;
        this.line = line;
        this.column = column;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
