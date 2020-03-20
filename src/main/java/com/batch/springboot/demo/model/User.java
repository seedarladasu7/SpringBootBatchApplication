package com.batch.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String userName;

	private String gender;

	private Integer age;

	private String pan;

	private String aadhar;

	private Double salary;

}
