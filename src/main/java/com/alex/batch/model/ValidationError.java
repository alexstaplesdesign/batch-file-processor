package com.alex.batch.model;

public record ValidationError(
        int rowNumber,
        String field,
        String message
) { }