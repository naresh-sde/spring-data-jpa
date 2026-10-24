package com.naresh.jpa.dto;

public record DepartmentResponse(
        Long id,
        String name,
        String location,
        long employeeCount) {
}