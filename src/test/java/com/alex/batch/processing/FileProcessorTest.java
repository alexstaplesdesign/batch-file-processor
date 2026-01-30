package com.alex.batch.processing;

import com.alex.batch.config.AppConfig;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileProcessorTest {

    @Test
    void process_writesOutputs_andArchivesInput() throws Exception {
        Path tempDir = Files.createTempDirectory("batch");
        Path in = Files.createDirectories(tempDir.resolve("in"));
        Path out = Files.createDirectories(tempDir.resolve("out"));
        Path arch = Files.createDirectories(tempDir.resolve("archive"));

        Path inputFile = in.resolve("customers.csv");
        Files.copy(Path.of("src/test/resources/fixtures/customers_valid.csv"), inputFile);

        AppConfig cfg = new AppConfig(in, out, arch, "*.csv", false);

        FileProcessor fp = new FileProcessor(cfg, new RecordValidator(), new RecordTransformer(new com.alex.batch.util.ClockProvider()));
        var result = fp.process(inputFile);

        assertTrue(Files.exists(result.processedOutputPath()));
        assertTrue(Files.exists(result.errorOutputPath()));
        assertTrue(Files.list(arch).findAny().isPresent());
    }
}