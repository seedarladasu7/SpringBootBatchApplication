package com.batch.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEmiDetailsDTO {
	private float loanPaid;
	private String paidOn;
	private float remainingLoanAmt;
}
