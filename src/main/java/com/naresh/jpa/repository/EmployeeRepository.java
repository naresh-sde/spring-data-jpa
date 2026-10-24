package com.naresh.jpa.repository;

import com.naresh.jpa.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// CRUD plus derived query methods and @Query. Spring Data builds the JPQL from the method name.
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // Paging + sorting
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    // Comparison keyword
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    // Multiple conditions
    List<Employee> findByDepartmentNameAndSalaryGreaterThan(String deptName, BigDecimal salary);

    // JPQL query
    @Query("select e from Employee e where e.salary >= :min and e.salary <= :max order by e.salary desc")
    List<Employee> findInSalaryRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // Native SQL query; select e.* (not *) so the joined departments.id is not mapped twice
    @Query(value = "select e.* from employees e join departments d on e.department_id = d.id where d.name = :deptName",
            nativeQuery = true)
    List<Employee> findByDepartmentNameNative(@Param("deptName") String deptName);

    long countByDepartmentId(Long departmentId);

    boolean existsByEmail(String email);
}