package com.incede.employee.DTO.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private Double salary;
    private Boolean isDeleted = false;
	public EmployeeDTO(String firstName, String lastName, String email, String designation, Double salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.designation = designation;
		this.salary = salary;
	}
    
    

    
}