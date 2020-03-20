package com.batch.springboot.demo.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.springboot.demo.entity.Employee;

public class ValidationProcessor implements ItemProcessor<Employee, Employee> {
	
	public Employee process(Employee user) throws Exception {
		/*if (user.getId() == null) {
			System.out.println("Missing employee id : " + user.getId());
			return null;
		}

		try {
			if (Integer.valueOf(user.getId()) <= 0) {
				System.out.println("Invalid employee id : " + user.getId());
				return null;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid employee id : " + user.getId());
			return null;
		}*/
		return user;
	}
}
