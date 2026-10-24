package com.naresh.jpa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        BigDecimal salary,
        LocalDate joinedOn,
        String departmentName) {
}