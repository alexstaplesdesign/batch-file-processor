package com.alex.batch.processing;

import com.alex.batch.model.CustomerRecord;
import com.alex.batch.model.ProcessedCustomerRecord;
import com.alex.batch.util.ClockProvider;

public final class RecordTransformer {
    private final ClockProvider clock;

    public RecordTransformer(ClockProvider clock) {
        this.clock = clock;
    }

    public ProcessedCustomerRecord transform(CustomerRecord r) {
        return new ProcessedCustomerRecord(
                r.customerId().trim(),
                toTitleCase(r.fullName()),
                r.email().trim().toLowerCase(),
                r.signupDate().trim(),
                clock.utcNowIsoInstant()
        );
    }

    private static String toTitleCase(String input) {
        if (input == null) return "";
        String[] parts = input.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            String t = Character.toUpperCase(p.charAt(0)) + p.substring(1);
            if (sb.length() > 0) sb.append(" ");
            sb.append(t);
        }
        return sb.toString();
    }
}