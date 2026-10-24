package com.naresh.jpa.controller;

import com.naresh.jpa.dto.DepartmentRequest;
import com.naresh.jpa.dto.DepartmentResponse;
import com.naresh.jpa.dto.EmployeeRequest;
import com.naresh.jpa.dto.EmployeeResponse;
import com.naresh.jpa.service.JpaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JpaController {

    private final JpaService jpaService;

    public JpaController(JpaService jpaService) {
        this.jpaService = jpaService;
    }

    // ---- Departments ----
    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(@RequestBody DepartmentRequest request) {
        return jpaService.createDepartment(request);
    }

    @GetMapping("/departments")
    public List<DepartmentResponse> getAllDepartments() {
        return jpaService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public DepartmentResponse getDepartment(@PathVariable Long id) {
        return jpaService.getDepartment(id);
    }

    @DeleteMapping("/departments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        jpaService.deleteDepartment(id);
    }

    // ---- Employees ----
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest request) {
        return jpaService.createEmployee(request);
    }

    @GetMapping("/employees/{id}")
    public EmployeeResponse getEmployee(@PathVariable Long id) {
        return jpaService.getEmployee(id);
    }

    @GetMapping("/departments/{deptId}/employees")
    public Page<EmployeeResponse> employeesByDepartment(@PathVariable Long deptId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return jpaService.getEmployeesByDepartment(deptId, page, size);
    }

    @GetMapping("/employees/high-salary/{min}")
    public List<EmployeeResponse> highSalary(@PathVariable BigDecimal min) {
        return jpaService.findBySalaryGreaterThan(min);
    }

    @GetMapping("/employees/salary-range")
    public List<EmployeeResponse> salaryRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return jpaService.findInSalaryRange(min, max);
    }

    @GetMapping("/employees/native/{deptName}")
    public List<EmployeeResponse> nativeQuery(@PathVariable String deptName) {
        return jpaService.findByDepartmentNative(deptName);
    }
}