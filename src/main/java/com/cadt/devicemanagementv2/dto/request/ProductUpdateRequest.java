package com.cadt.devicemanagementv2.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @Size(max = 200) String title,
        @Size(max = 100) String brand,
        @Size(max = 120) String model,
        @Size(max = 120) String cpu,
        @Min(2) @Max(128) Integer ramGb,
        String storageType,
        @Min(64) @Max(8192) Integer storageGb,
        @DecimalMin("10.0") @DecimalMax("20.0") Double screenSize,
        String grade,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @Size(max = 8) String currency,
        @Min(0) Integer quantity,
        @Min(0) @Max(36) Integer warrantyMonths,
        @Size(max = 500) String primaryImageUrl,
        @Size(max = 500) String conditionNotes,
        String specsJson
) {
}
