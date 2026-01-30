package com.alex.batch.processing;

import com.alex.batch.config.AppConfig;
import com.alex.batch.io.FileDiscoverer;
import com.alex.batch.model.ProcessingResult;
import com.alex.batch.util.Metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public final class BatchProcessor {
    private static final Logger log = LoggerFactory.getLogger(BatchProcessor.class);

    private final AppConfig config;
    private final RecordValidator validator;
    private final RecordTransformer transformer;

    public BatchProcessor(AppConfig config, RecordValidator validator, RecordTransformer transformer) {
        this.config = config;
        this.validator = validator;
        this.transformer = transformer;
    }

    public int run() throws Exception {
        Metrics m = new Metrics();
        long start = System.currentTimeMillis();

        List<Path> files = new FileDiscoverer().discover(config.inputDir(), config.pattern());
        log.info("Discovered {} file(s) in {}", files.size(), config.inputDir());

        boolean anyValidationIssues = false;

        for (Path f : files) {
            ProcessingResult r = new FileProcessor(config, validator, transformer).process(f);
            m.add(r);

            if (r.invalidRows() > 0) {
                anyValidationIssues = true;
                log.warn("{} had {} validation error(s)", r.inputFileName(), r.invalidRows());
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        log.info("Run complete: files={} totalRows={} validRows={} invalidRows={} timeMs={}",
                m.files(), m.totalRows(), m.validRows(), m.invalidRows(), elapsed);

        if (config.failOnError() && anyValidationIssues) {
            return 4;
        }
        return 0;
    }
}