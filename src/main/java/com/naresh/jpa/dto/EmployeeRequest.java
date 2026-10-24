package com.naresh.jpa.dto;

import java.math.BigDecimal;

public record EmployeeRequest(
        String name,
        String email,
        BigDecimal salary,
        Long departmentId) {
}