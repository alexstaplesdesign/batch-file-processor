package com.alex.batch.model;

public record ProcessedCustomerRecord(
        String customerId,
        String fullName,
        String email,
        String signupDate,
        String processedAt
) { }