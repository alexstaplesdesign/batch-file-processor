package com.alex.batch.io;

import com.alex.batch.model.ProcessedCustomerRecord;
import com.alex.batch.model.ValidationError;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CsvWriter {

    public void writeProcessed(Path outFile, List<ProcessedCustomerRecord> rows) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(outFile)) {
            bw.write("customer_id,full_name,email,signup_date,processed_at");
            bw.newLine();
            for (ProcessedCustomerRecord r : rows) {
                bw.write(String.join(",",
                        safe(r.customerId()),
                        safe(r.fullName()),
                        safe(r.email()),
                        safe(r.signupDate()),
                        safe(r.processedAt())
                ));
                bw.newLine();
            }
        }
    }

    public void writeErrors(Path outFile, List<ValidationError> errors) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(outFile)) {
            bw.write("row_number,field,message");
            bw.newLine();
            for (ValidationError e : errors) {
                bw.write(e.rowNumber() + "," + safe(e.field()) + "," + safe(e.message()));
                bw.newLine();
            }
        }
    }

    private static String safe(String s) {
        return s == null ? "" : s.replace("\n", " ").replace("\r", " ").trim();
    }
}