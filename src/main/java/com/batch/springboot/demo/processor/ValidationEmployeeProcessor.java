package com.batch.springboot.demo.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.service.EmployeeService;

@Component
public class ValidationEmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Autowired
	EmployeeService employeeService;

	public Employee process(Employee user) throws Exception {
		System.out.println("Processing Employee: " + user);
		/*
		 * if (user.getId() == null) { System.out.println("Missing employee id : " +
		 * user.getId()); return null; }
		 * 
		 * try { if (Integer.valueOf(user.getId()) <= 0) {
		 * System.out.println("Invalid employee id : " + user.getId()); return null; } }
		 * catch (NumberFormatException e) { System.out.println("Invalid employee id : "
		 * + user.getId()); return null; }
		 */
		return user;
	}
}
