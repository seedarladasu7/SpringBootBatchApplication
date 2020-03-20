package com.batch.springboot.demo.model;

import lombok.Data;

@Data
public class LoanRequest {

	private int empId;
	private double loanAmount;
	private int tenure;

}
