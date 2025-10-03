package com.cadt.devicemanagementv2.utils;

public final class ApiResponseExtensions {
    private ApiResponseExtensions(){}

    public static <T> ApiResponse<T> with(ApiResponse<T> base, T data){
        return new ApiResponse<>(base.success(), base.message(), data);
    }
}
