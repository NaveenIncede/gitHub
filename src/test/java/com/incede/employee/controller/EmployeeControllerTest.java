package com.incede.employee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.incede.employee.DTO.employeeDTO.EmployeeDTO;
import com.incede.employee.Entity.employeeEntity.Employee;
import com.incede.employee.controller.EmployeeController.EmployeeController;
import com.incede.employee.repository.EmployeeRepository.EmployeeRepository;
import com.incede.employee.service.employeeService.EmployeeService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;
    
    @Mock
    private EmployeeRepository employeeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testSaveOrUpdateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO("John", "Doe", "john@example.com", "Developer", 100000.0);

        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/api/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.designation").value("Developer"))
                .andExpect(jsonPath("$.salary").value(100000.0));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        // Create a mock employee object
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDesignation("Developer");
        employee.setSalary(100000.0);
        employee.setIsDeleted(false);

        // Mock the repository methods
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee); // Mock save

        // Perform the DELETE API call
        mockMvc.perform(post("/api/employee/delete/{id}", employeeId))  // Use `post()` as per your controller
                .andExpect(status().isOk())  // Expect status 200 OK
                .andExpect(content().string("Employee soft deleted successfully."));  // Expect success message

        // Verify that deleteEmployee method was called once
        verify(employeeRepository, times(1)).findById(employeeId); // Check if findById was called once
        verify(employeeRepository, times(1)).save(any(Employee.class)); // Verify if save was called once
    }



    @Test
    public void testGetAllEmployees() throws Exception {
        // Creating some hardcoded EmployeeDTO data
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john.doe@example.com");
        employee1.setDesignation("Developer");
        employee1.setSalary(100000.0);

        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmail("jane.doe@example.com");
        employee2.setDesignation("Manager");
        employee2.setSalary(120000.0);

        // Adding the employees to a list
        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        // Mock the service to return the hardcoded employees
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk()) // Check that the status is OK
                .andExpect(jsonPath("$[0].firstName").value("John")) // Check first employee's first name
                .andExpect(jsonPath("$[1].firstName").value("Jane")); // Check second employee's first name
    }


    @Test
    public void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO("John", "Doe", "john@example.com", "Developer", 50000.0);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDTO);

        mockMvc.perform(get("/api/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
