package com.batch.springboot.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.entity.LoanAccount;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {

	List<LoanAccount> findByEmployee(Employee employee);

}
