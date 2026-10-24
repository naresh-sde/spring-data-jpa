package com.naresh.jpa.service;

import com.naresh.jpa.dto.DepartmentRequest;
import com.naresh.jpa.dto.DepartmentResponse;
import com.naresh.jpa.dto.EmployeeRequest;
import com.naresh.jpa.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface JpaService {

    // Departments
    DepartmentResponse createDepartment(DepartmentRequest request);
    DepartmentResponse getDepartment(Long id);
    List<DepartmentResponse> getAllDepartments();

    // Employees
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse getEmployee(Long id);
    Page<EmployeeResponse> getEmployeesByDepartment(Long deptId, int page, int size);
    List<EmployeeResponse> findBySalaryGreaterThan(BigDecimal salary);
    List<EmployeeResponse> findInSalaryRange(BigDecimal min, BigDecimal max);
    List<EmployeeResponse> findByDepartmentNative(String deptName);
    void deleteDepartment(Long id);
}