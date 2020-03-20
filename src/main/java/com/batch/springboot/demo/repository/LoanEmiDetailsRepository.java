package com.batch.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch.springboot.demo.entity.LoanEmiDetails;

public interface LoanEmiDetailsRepository extends JpaRepository<LoanEmiDetails, Integer>{

}
