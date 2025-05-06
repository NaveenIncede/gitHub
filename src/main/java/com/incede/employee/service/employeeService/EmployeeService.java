package com.incede.employee.service.employeeService;

import com.incede.employee.DTO.employeeDTO.EmployeeDTO;
import com.incede.employee.Entity.employeeEntity.Employee;
import com.incede.employee.repository.EmployeeRepository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Save or update employee
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        if (employee.getIsDeleted() == null) {
            employee.setIsDeleted(false); // default to false
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee);
    }

    // Return only non-deleted employees
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .filter(emp -> !Boolean.TRUE.equals(emp.getIsDeleted()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent() && !Boolean.TRUE.equals(optionalEmployee.get().getIsDeleted())) {
            return mapToDTO(optionalEmployee.get());
        }
        throw new RuntimeException("Employee not found with ID: " + id);
    }

    // Soft delete (set isDeleted to true)
    public boolean deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setIsDeleted(true);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    // Convert DTO to Entity
    private Employee mapToEntity(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setDesignation(dto.getDesignation());
        emp.setSalary(dto.getSalary());
        emp.setIsDeleted(dto.getIsDeleted()); // handle soft delete field
        return emp;
    }

    // Convert Entity to DTO
    private EmployeeDTO mapToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(emp.getId());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setEmail(emp.getEmail());
        dto.setDesignation(emp.getDesignation());
        dto.setSalary(emp.getSalary());
        dto.setIsDeleted(emp.getIsDeleted());
        return dto;
    }
}
