package com.naresh.jpa.service.impl;

import com.naresh.jpa.dto.DepartmentRequest;
import com.naresh.jpa.dto.DepartmentResponse;
import com.naresh.jpa.dto.EmployeeRequest;
import com.naresh.jpa.dto.EmployeeResponse;
import com.naresh.jpa.exception.ResourceNotFoundException;
import com.naresh.jpa.model.Department;
import com.naresh.jpa.model.Employee;
import com.naresh.jpa.repository.DepartmentRepository;
import com.naresh.jpa.repository.EmployeeRepository;
import com.naresh.jpa.service.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class JpaServiceImpl implements JpaService {

    private static final Logger log = LoggerFactory.getLogger(JpaServiceImpl.class);

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public JpaServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    // ===== Departments =====
    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department saved = departmentRepository.save(new Department(request.name(), request.location()));
        log.info("Created department {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public DepartmentResponse getDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));
        return toResponse(dept);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found: " + id);
        }
        departmentRepository.deleteById(id);
        log.info("Deleted department {}", id);
    }

    // ===== Employees =====
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Department dept = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + request.departmentId()));
        Employee emp = new Employee(request.name(), request.email(), request.salary(), LocalDate.now(), dept);
        EmployeeResponse created = toResponse(employeeRepository.save(emp));
        log.info("Created employee {} in department {}", created.id(), request.departmentId());
        return created;
    }

    @Override
    public EmployeeResponse getEmployee(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return toResponse(emp);
    }

    @Override
    public Page<EmployeeResponse> getEmployeesByDepartment(Long deptId, int page, int size) {
        // PageRequest carries the sort, so the ORDER BY lives in the query, not in post-processing
        PageRequest pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeRepository.findByDepartmentId(deptId, pageable)
                .map(this::toResponse);
    }

    @Override
    public List<EmployeeResponse> findBySalaryGreaterThan(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThan(salary).stream().map(this::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findInSalaryRange(BigDecimal min, BigDecimal max) {
        return employeeRepository.findInSalaryRange(min, max).stream().map(this::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByDepartmentNative(String deptName) {
        return employeeRepository.findByDepartmentNameNative(deptName).stream().map(this::toResponse).toList();
    }

    // ===== Mapping =====
    private DepartmentResponse toResponse(Department d) {
        return new DepartmentResponse(d.getId(), d.getName(), d.getLocation(),
                d.getEmployees() != null ? d.getEmployees().size() : 0);
    }

    private EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(e.getId(), e.getName(), e.getEmail(), e.getSalary(), e.getJoinedOn(),
                e.getDepartment() == null ? null : e.getDepartment().getName());
    }
}