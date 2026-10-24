package com.naresh.jpa.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

// Entity: Employee. This side owns the department_id foreign key.
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private BigDecimal salary;

    private LocalDate joinedOn;

    // Optimistic locking: Hibernate bumps this on every update and fails with
    // ObjectOptimisticLockingFailureException if the row changed in the meantime.
    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    protected Employee() {}

    public Employee(String name, String email, BigDecimal salary, LocalDate joinedOn, Department department) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.joinedOn = joinedOn;
        this.department = department;
    }

    public Long getId() { return id; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public LocalDate getJoinedOn() { return joinedOn; }
    public void setJoinedOn(LocalDate joinedOn) { this.joinedOn = joinedOn; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}