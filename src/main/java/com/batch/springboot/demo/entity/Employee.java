package com.batch.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "EMPLOYEE")
@Data
@ToString
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Integer empId;
	
	@Column(name = "emp_name")
    private String empName;
	
	@Column
    private String gender;
	
	@Column
    private Integer age;
    
	@Column
	private String pan;
    
	@Column
    private String aadhar;
    
	@Column
	private Float salary;
	
	
}