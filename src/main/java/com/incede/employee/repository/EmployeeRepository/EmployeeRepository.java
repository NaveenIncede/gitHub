package com.incede.employee.repository.EmployeeRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.employee.Entity.employeeEntity.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query methods can be added here if needed
}