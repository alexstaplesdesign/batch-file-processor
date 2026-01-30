package com.alex.batch.io;

import com.alex.batch.model.CustomerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class CsvReader {

    public List<CustomerRecord> readCustomers(Path file) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String header = br.readLine();
            if (header == null) {
                return List.of();
            }

            String[] cols = header.split(",", -1);
            Map<String, Integer> index = indexColumns(cols);

            requireColumn(index, "customer_id");
            requireColumn(index, "full_name");
            requireColumn(index, "email");
            requireColumn(index, "signup_date");

            List<CustomerRecord> records = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                records.add(new CustomerRecord(
                        get(parts, index.get("customer_id")),
                        get(parts, index.get("full_name")),
                        get(parts, index.get("email")),
                        get(parts, index.get("signup_date"))
                ));
            }
            return records;
        }
    }

    private static Map<String, Integer> indexColumns(String[] cols) {
        Map<String, Integer> index = new HashMap<>();
        for (int i = 0; i < cols.length; i++) {
            index.put(cols[i].trim(), i);
        }
        return index;
    }

    private static void requireColumn(Map<String, Integer> index, String name) {
        if (!index.containsKey(name)) {
            throw new IllegalArgumentException("Missing required column: " + name);
        }
    }

    private static String get(String[] parts, int idx) {
        if (idx < 0 || idx >= parts.length) {
            return "";
        }
        return parts[idx].trim();
    }
}