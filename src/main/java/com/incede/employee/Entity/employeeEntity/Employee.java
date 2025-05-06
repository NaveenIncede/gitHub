package com.incede.employee.Entity.employeeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


 
@Entity
@Table(name= "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "employees")
public class Employee {
	
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private Double salary;
    private Boolean isDeleted = false;
}