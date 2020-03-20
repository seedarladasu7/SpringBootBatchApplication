package com.batch.springboot.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.repository.EmployeeRepository;
import com.batch.springboot.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeRepository empRepository;

	@Override
	public Employee getEmployee(int emId) {
		Optional<Employee> empOpt =  empRepository.findById(emId);
		if(empOpt.isPresent())
			return empOpt.get();
		return null;
	}
	
	

}
