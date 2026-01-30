package com.alex.batch.io;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderTest {

    @Test
    void readCustomers_readsFixture() throws Exception {
        CsvReader r = new CsvReader();
        Path p = Path.of("src/test/resources/fixtures/customers_valid.csv");
        var rows = r.readCustomers(p);
        assertEquals(2, rows.size());
    }
}