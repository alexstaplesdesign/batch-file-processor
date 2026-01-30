package com.alex.batch.processing;

import com.alex.batch.model.CustomerRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordValidatorTest {

    @Test
    void validRecord_hasNoErrors() {
        RecordValidator v = new RecordValidator();
        CustomerRecord r = new CustomerRecord("1", "Jane Doe", "jane@example.com", "2025-12-01");
        assertTrue(v.validate(r, 1).isEmpty());
    }

    @Test
    void missingEmail_hasErrors() {
        RecordValidator v = new RecordValidator();
        CustomerRecord r = new CustomerRecord("1", "Jane Doe", "", "2025-12-01");
        assertFalse(v.validate(r, 1).isEmpty());
    }
}