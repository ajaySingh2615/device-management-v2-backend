package com.cadt.devicemanagementv2.dto.request;

import jakarta.validation.constraints.*;

public record SampleCreateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 120, message = "Name must be <= 120 chars")
        String name,

        @Email(message = "Invalid email format")
        @Size(max = 180, message = "Email must be <= 180 chars")
        String email,

        @Min(value = 0, message = "Price cannot be negative")
        @Max(value = 10_000_000, message = "Price too large")
        Integer price
) {
}
