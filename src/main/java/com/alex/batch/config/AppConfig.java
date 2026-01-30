package com.alex.batch.config;

import java.nio.file.Path;

public record AppConfig(
        Path inputDir,
        Path outputDir,
        Path archiveDir,
        String pattern,
        boolean failOnError
) { }