package com.batch.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch.springboot.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
