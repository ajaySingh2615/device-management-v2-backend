package com.cadt.devicemanagementv2.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String path,
        int status,
        String error,
        String message,
        Instant timestamp,
        Map<String, Object> details
) {
}
