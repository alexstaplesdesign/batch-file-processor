package com.alex.batch.io;

import com.alex.batch.model.ProcessedCustomerRecord;
import com.alex.batch.model.ValidationError;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvWriterTest {

    @Test
    void writeProcessed_createsFile() throws Exception {
        Path tmp = Files.createTempFile("processed", ".csv");
        new CsvWriter().writeProcessed(tmp, List.of(
                new ProcessedCustomerRecord("1", "Jane Doe", "jane@example.com", "2025-12-01", "2025-12-01T00:00:00Z")
        ));
        assertTrue(Files.size(tmp) > 0);
    }

    @Test
    void writeErrors_createsFile() throws Exception {
        Path tmp = Files.createTempFile("errors", ".csv");
        new CsvWriter().writeErrors(tmp, List.of(
                new ValidationError(1, "email", "Required")
        ));
        assertTrue(Files.size(tmp) > 0);
    }
}