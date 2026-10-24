package com.naresh.jpa.config;

import com.naresh.jpa.model.Department;
import com.naresh.jpa.model.Employee;
import com.naresh.jpa.repository.DepartmentRepository;
import com.naresh.jpa.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

// Seeds H2 with sample departments and employees so the query endpoints return data.
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DataSeeder(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        // data.sql already inserted the sample rows; re-inserting here would break
        // the UNIQUE constraints on departments.name and employees.email
        if (departmentRepository.count() > 0) {
            log.info("Sample data already present from data.sql - skipping JPA seed");
            return;
        }

        Department it = departmentRepository.save(new Department("IT", "Hyderabad"));
        Department hr = departmentRepository.save(new Department("HR", "Bengaluru"));

        employeeRepository.save(new Employee("Alice", "alice@it.com", new BigDecimal("95000"),
                LocalDate.of(2022, 1, 10), it));
        employeeRepository.save(new Employee("Bob", "bob@it.com", new BigDecimal("75000"),
                LocalDate.of(2023, 3, 1), it));
        employeeRepository.save(new Employee("Carol", "carol@hr.com", new BigDecimal("60000"),
                LocalDate.of(2021, 6, 15), hr));

        log.info("Seed data loaded: 2 departments, 3 employees");
    }
}