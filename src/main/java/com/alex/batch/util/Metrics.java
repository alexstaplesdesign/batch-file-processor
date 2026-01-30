package com.alex.batch.util;

import com.alex.batch.model.ProcessingResult;

public final class Metrics {
    private int files;
    private int totalRows;
    private int validRows;
    private int invalidRows;

    public void add(ProcessingResult r) {
        files++;
        totalRows += r.totalRows();
        validRows += r.validRows();
        invalidRows += r.invalidRows();
    }

    public int files() { return files; }
    public int totalRows() { return totalRows; }
    public int validRows() { return validRows; }
    public int invalidRows() { return invalidRows; }
}