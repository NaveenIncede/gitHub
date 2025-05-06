package com.incede.employee.controller.EmployeeController;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.employee.DTO.employeeDTO.EmployeeDTO;
import com.incede.employee.service.employeeService.EmployeeService;

//created by naveen
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Save or update an employee
    @PostMapping("/save")
    public ResponseEntity<EmployeeDTO> saveOrUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    // Soft delete using POST
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.ok("Employee soft deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all active (non-deleted) employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
