package com.alex.batch.model;

import java.nio.file.Path;

public record ProcessingResult(
        String inputFileName,
        int totalRows,
        int validRows,
        int invalidRows,
        Path processedOutputPath,
        Path errorOutputPath
) { }