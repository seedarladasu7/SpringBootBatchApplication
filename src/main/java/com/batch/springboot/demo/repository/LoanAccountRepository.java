package com.batch.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.springboot.demo.entity.LoanAccount;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer>{
	
	

}
