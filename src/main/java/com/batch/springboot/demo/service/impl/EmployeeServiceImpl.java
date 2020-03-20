package com.batch.springboot.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.entity.LoanEmiDetails;
import com.batch.springboot.demo.entity.RateOfInterest;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RateOfInterestRequest;
import com.batch.springboot.demo.model.RoiRequest;
import com.batch.springboot.demo.repository.EmployeeRepository;
import com.batch.springboot.demo.repository.LoanAccountRepository;
import com.batch.springboot.demo.repository.RoiRepository;
import com.batch.springboot.demo.service.EmployeeService;
import com.batch.springboot.demo.util.EmiCalculator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	RoiRepository roiRepository;

	@Autowired
	LoanAccountRepository loanAccountRepository;

	@Override
	public Employee getEmployee(int emId) {
		Optional<Employee> empOpt = empRepository.findById(emId);
		if (empOpt.isPresent())
			return empOpt.get();
		return null;
	}

	@Override
	public String registerRateOfInterest(RateOfInterestRequest roiRequest) {
		
		for(RoiRequest roiReq: roiRequest.getReteOfInterests()) {
			RateOfInterest roi = new RateOfInterest();

			roi.setSalaryFrom(roiReq.getSalaryFrom());
			roi.setSalaryTo(roiReq.getSalaryTo());
			roi.setRoi(roiReq.getRoi());

			roiRepository.save(roi);
		}

		

		return "Rate Of Interest registered successfully..";
	}

	@Override
	public String applyForLoan(LoanRequest loanRequest) {

		Employee emp = getEmployee(loanRequest.getEmpId());

		if (Optional.ofNullable(emp).isPresent()) {
			RateOfInterest roi = roiRepository.findByRoiBetweenLoanFromAndLoanTo(emp.getSalary());
			if (Optional.ofNullable(roi).isPresent()) {
				float emiValue = EmiCalculator.calculateEmi(loanRequest.getLoanAmount(), roi.getRoi(),
						loanRequest.getTenure());
			
				LoanAccount loanAcc = new LoanAccount();
				loanAcc.setEmi(emiValue);
				loanAcc.setLoanRequested(loanRequest.getLoanAmount());
				loanAcc.setRateOfInterest(roi.getRoi());
				loanAcc.setEmployee(emp);

				Set<LoanEmiDetails> loanEmiDetails = new HashSet<>();

				LoanEmiDetails loanEmi = new LoanEmiDetails();
				loanEmi.setEmiPaid(emiValue);
				loanEmi.setPaidOn(java.sql.Date.valueOf(simpDate.format(new Date())));
				loanEmi.setRemainingLoanAmt(loanRequest.getLoanAmount() - emiValue);

				loanEmiDetails.add(loanEmi);

				loanAcc.setLoanEmiDetails(loanEmiDetails);

				loanAccountRepository.saveAndFlush(loanAcc);

				return "Loan processing done successfully...";
			}
		}

		return "Loan processing failed ... ";
	}

}
