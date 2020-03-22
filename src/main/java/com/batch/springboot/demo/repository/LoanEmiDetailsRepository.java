package com.batch.springboot.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.entity.LoanEmiDetails;

public interface LoanEmiDetailsRepository extends JpaRepository<LoanEmiDetails, Integer>{
	
	public List<LoanEmiDetails> findByLoanAccount(LoanAccount loanAccount);
	

}
