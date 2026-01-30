package com.alex.batch.model;

public record CustomerRecord(
        String customerId,
        String fullName,
        String email,
        String signupDate
) { }