package com.cadt.devicemanagementv2.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank(message = "SKU is required")
        @Size(max = 64) String sku,

        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 100) String brand,
        @NotBlank @Size(max = 120) String model,

        @Size(max = 120) String cpu,

        @NotNull @Min(2) @Max(128) Integer ramGb,

        @NotBlank String storageType, // HDD/SSD/NVME
        @NotNull @Min(64) @Max(8192) Integer storageGb,

        @DecimalMin("10.0") @DecimalMax("20.0") Double screenSize,

        @NotBlank String grade,  // A/B/C

        @NotNull @DecimalMin(value = "0.0", inclusive = false)BigDecimal price,
        @NotNull @Size(max = 8) String currency,

        @NotNull @Min(0) Integer quantity,

        @NotNull @Min(0) @Max(36) Integer warrantyMonths,

        @Size(max = 500) String primaryImageUrl,
        @Size(max = 500) String conditionNotes,

        String specsJson
        ) {
}
