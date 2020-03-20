package com.batch.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RateOfInterestRequest;
import com.batch.springboot.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/{empId}")
	public ResponseEntity<Employee> findEmployee(@PathVariable("empId") String empId) {
		return new ResponseEntity<>(employeeService.getEmployee(Integer.valueOf(empId)), HttpStatus.OK);
	}

	@PostMapping("/registerRoi")
	public ResponseEntity<String> registerRateOfInterest(@RequestBody RateOfInterestRequest roiRequest) {
		return new ResponseEntity<>(employeeService.registerRateOfInterest(roiRequest), HttpStatus.OK);
	}

	@PostMapping("/loan/apply")
	public ResponseEntity<String> applyForLoan(@RequestBody LoanRequest loanRequest) {
		return new ResponseEntity<>(employeeService.applyForLoan(loanRequest), HttpStatus.OK);
	}

}
