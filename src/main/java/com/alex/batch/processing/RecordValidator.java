package com.alex.batch.processing;

import com.alex.batch.model.CustomerRecord;
import com.alex.batch.model.ValidationError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class RecordValidator {

    public List<ValidationError> validate(CustomerRecord r, int rowNumber) {
        List<ValidationError> errors = new ArrayList<>();

        if (isBlank(r.customerId())) {
            errors.add(new ValidationError(rowNumber, "customer_id", "Required"));
        } else {
            try {
                int id = Integer.parseInt(r.customerId());
                if (id <= 0) {
                    errors.add(new ValidationError(rowNumber, "customer_id", "Must be > 0"));
                }
            } catch (NumberFormatException e) {
                errors.add(new ValidationError(rowNumber, "customer_id", "Must be an integer"));
            }
        }

        if (isBlank(r.fullName())) {
            errors.add(new ValidationError(rowNumber, "full_name", "Required"));
        } else if (r.fullName().length() < 2 || r.fullName().length() > 80) {
            errors.add(new ValidationError(rowNumber, "full_name", "Length must be 2..80"));
        }

        if (isBlank(r.email())) {
            errors.add(new ValidationError(rowNumber, "email", "Required"));
        } else if (!r.email().contains("@") || r.email().endsWith("@")) {
            errors.add(new ValidationError(rowNumber, "email", "Invalid format"));
        }

        if (isBlank(r.signupDate())) {
            errors.add(new ValidationError(rowNumber, "signup_date", "Required"));
        } else {
            try {
                LocalDate.parse(r.signupDate());
            } catch (Exception e) {
                errors.add(new ValidationError(rowNumber, "signup_date", "Must be YYYY-MM-DD"));
            }
        }

        return errors;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}