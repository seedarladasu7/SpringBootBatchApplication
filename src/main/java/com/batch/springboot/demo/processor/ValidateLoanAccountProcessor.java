package com.batch.springboot.demo.processor;

import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.service.EmployeeService;

@Component
public class ValidateLoanAccountProcessor implements ItemProcessor<LoanAccount, LoanAccount> {

	@Autowired
	EmployeeService employeeService;

	public LoanAccount process(LoanAccount loanAccount) throws Exception {
		System.out.println("Processing LoanAccount: " + loanAccount);

		if (Optional.ofNullable(loanAccount).isPresent()) {
			if (loanAccount.getStatus().equalsIgnoreCase("open")) {
				employeeService.diductEmi(loanAccount);
			}
		}

		/*
		 * if (user.getId() == null) { System.out.println("Missing employee id : " +
		 * user.getId()); return null; }
		 * 
		 * try { if (Integer.valueOf(user.getId()) <= 0) {
		 * System.out.println("Invalid employee id : " + user.getId()); return null; } }
		 * catch (NumberFormatException e) { System.out.println("Invalid employee id : "
		 * + user.getId()); return null; }
		 */
		return loanAccount;
	}
}
