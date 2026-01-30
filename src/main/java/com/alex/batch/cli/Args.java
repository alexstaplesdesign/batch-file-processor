package com.alex.batch.cli;

import java.nio.file.Path;

public record Args(
        Path inputDir,
        Path outputDir,
        Path archiveDir,
        String pattern,
        boolean failOnError
) { }