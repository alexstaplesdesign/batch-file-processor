package com.alex.batch.processing;

import com.alex.batch.config.AppConfig;
import com.alex.batch.io.ArchiveService;
import com.alex.batch.io.CsvReader;
import com.alex.batch.io.CsvWriter;
import com.alex.batch.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class FileProcessor {
    private static final Logger log = LoggerFactory.getLogger(FileProcessor.class);

    private final AppConfig config;
    private final CsvReader reader;
    private final CsvWriter writer;
    private final ArchiveService archiver;
    private final RecordValidator validator;
    private final RecordTransformer transformer;

    public FileProcessor(AppConfig config, RecordValidator validator, RecordTransformer transformer) {
        this.config = config;
        this.validator = validator;
        this.transformer = transformer;
        this.reader = new CsvReader();
        this.writer = new CsvWriter();
        this.archiver = new ArchiveService();
    }

    public ProcessingResult process(Path inputFile) throws Exception {
        String baseName = inputFile.getFileName().toString();

        Path processedOut = config.outputDir().resolve(baseName + ".processed.csv");
        Path errorsOut = config.outputDir().resolve(baseName + ".errors.csv");

        List<CustomerRecord> rows = reader.readCustomers(inputFile);

        List<ProcessedCustomerRecord> processed = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        int rowNumber = 1;
        for (CustomerRecord r : rows) {
            List<ValidationError> rowErrors = validator.validate(r, rowNumber);
            if (rowErrors.isEmpty()) {
                processed.add(transformer.transform(r));
            } else {
                errors.addAll(rowErrors);
            }
            rowNumber++;
        }

        writer.writeProcessed(processedOut, processed);
        writer.writeErrors(errorsOut, errors);

        archiver.archive(inputFile, config.archiveDir());

        log.info("Processed {} row(s) from {}", rows.size(), baseName);

        return new ProcessingResult(
                baseName,
                rows.size(),
                processed.size(),
                errors.size(),
                processedOut,
                errorsOut
        );
    }
}