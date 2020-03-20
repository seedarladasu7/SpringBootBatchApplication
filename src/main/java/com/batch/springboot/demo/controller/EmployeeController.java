package com.batch.springboot.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	@GetMapping("/{empId}")
	public ResponseEntity<Employee> findEmployee(@PathVariable("empId") String empId) {
		return new ResponseEntity<>(employeeService.getEmployee(Integer.valueOf(empId)), HttpStatus.OK);
	}
	
	@GetMapping("/loan/apply")
	public ResponseEntity<Employee> applyForLoan(@RequestBody LoanRequest loanRequest) {
		
		Employee emp = employeeService.getEmployee(Integer.valueOf(loanRequest.getEmpId()));
		
		if(Optional.ofNullable(emp).isPresent()) {
			
		} else {
			log.debug("Employee not found with id: "+loanRequest.getEmpId());
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
