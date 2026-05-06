package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/api/v1/admin/test")
    public ApiResponse<String> adminTest() {
        return ApiResponse.success("ADMIN ENDPOINT");
    }

    @GetMapping("/api/v1/employee/test")
    public ApiResponse<String> employeeTest() {
        return ApiResponse.success("EMPLOYEE ENDPOINT");
    }
}