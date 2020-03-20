package com.batch.springboot.demo.model;

import lombok.Data;

@Data
public class LoanRequest {

	private int empId;
	private float loanAmount;
	private int tenure;

}
