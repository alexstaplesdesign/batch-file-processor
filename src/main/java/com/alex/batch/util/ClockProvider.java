package com.alex.batch.util;

import java.time.Instant;

public final class ClockProvider {
    public String utcNowIsoInstant() {
        return Instant.now().toString();
    }
}