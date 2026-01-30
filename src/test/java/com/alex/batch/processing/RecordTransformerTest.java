package com.alex.batch.processing;

import com.alex.batch.model.CustomerRecord;
import com.alex.batch.util.ClockProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordTransformerTest {

    @Test
    void transform_normalizesFields() {
        RecordTransformer t = new RecordTransformer(new ClockProvider());
        var out = t.transform(new CustomerRecord("7", "jANE dOE", "JANE@EXAMPLE.COM", "2025-12-01"));
        assertEquals("Jane Doe", out.fullName());
        assertEquals("jane@example.com", out.email());
    }
}