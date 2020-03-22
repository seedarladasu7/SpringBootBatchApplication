package com.batch.springboot.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAccountDTO {
	
	private int loanId;
	private float loanRequested;
	private float rateOfInterest;
	private float emi;
	private String status;	
	private float remainingLoanAmt;
	private List<LoanEmiDetailsDTO> emiDtails;
}
