package com.batch.springboot.demo.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.batch.springboot.demo.entity.Employee;

public class EmployeeMapper implements FieldSetMapper<Employee>{

	@Override
	public Employee mapFieldSet(FieldSet fieldSet) throws BindException {

		Employee emp = new Employee();
		return emp;
	}

}
