package com.batch.springboot.demo.config;
 
import org.springframework.batch.item.ItemProcessor;

import com.batch.springboot.demo.entity.Employee;
 
public class DBLogProcessor implements ItemProcessor<Employee, Employee>
{
    public Employee process(Employee employee) throws Exception
    {
        System.out.println("Inserting employee : " + employee);
        return employee;
    }
}