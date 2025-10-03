package com.cadt.devicemanagementv2.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
