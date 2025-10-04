package com.cadt.devicemanagementv2.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String title,
        String brand,
        String model,
        String cpu,
        Integer ramGb,
        String storageType,
        Integer storageGb,
        Double screenSize,
        String grade,
        BigDecimal price,
        String currency,
        Integer quantity,
        Integer warrantyMonths,
        String primaryImageUrl,
        String conditionNotes,
        String specsJson,
        String createdAt,
        String updatedAt
) {
}
