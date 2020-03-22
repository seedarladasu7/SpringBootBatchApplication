package com.batch.springboot.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.entity.LoanEmiDetails;
import com.batch.springboot.demo.entity.RateOfInterest;
import com.batch.springboot.demo.model.EmployeeLoanDetails;
import com.batch.springboot.demo.model.LoanAccountDTO;
import com.batch.springboot.demo.model.LoanEmiDetailsDTO;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RateOfInterestRequest;
import com.batch.springboot.demo.model.RoiRequest;
import com.batch.springboot.demo.repository.EmployeeRepository;
import com.batch.springboot.demo.repository.LoanAccountRepository;
import com.batch.springboot.demo.repository.LoanEmiDetailsRepository;
import com.batch.springboot.demo.repository.RoiRepository;
import com.batch.springboot.demo.service.EmployeeService;
import com.batch.springboot.demo.util.EmiCalculator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	RoiRepository roiRepository;

	@Autowired
	LoanAccountRepository loanAccountRepository;

	@Autowired
	LoanEmiDetailsRepository loanEmiDetailsRepository;

	@Override
	public Employee getEmployee(int emId) {
		Optional<Employee> empOpt = empRepository.findById(emId);
		if (empOpt.isPresent())
			return empOpt.get();
		return null;
	}

	@Override
	public String registerRateOfInterest(RateOfInterestRequest roiRequest) {

		for (RoiRequest roiReq : roiRequest.getReteOfInterests()) {
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

				// float interest = ((emiValue * loanRequest.getTenure() * 12) -
				// loanRequest.getLoanAmount());

				LoanAccount loanAcc = new LoanAccount();
				loanAcc.setEmi(emiValue);
				loanAcc.setLoanRequested(loanRequest.getLoanAmount());
				loanAcc.setRateOfInterest(roi.getRoi());
				loanAcc.setStatus("open");
				loanAcc.setEmployee(emp);
				loanAcc = loanAccountRepository.saveAndFlush(loanAcc);

				LoanEmiDetails loanEmi = new LoanEmiDetails();
				loanEmi.setEmiPaid(emiValue);
				loanEmi.setPaidOn(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
				// loanEmi.setRemainingLoanAmt((loanRequest.getLoanAmount() + interest) -
				// emiValue);
				loanEmi.setRemainingLoanAmt(loanRequest.getLoanAmount() - emiValue);
				loanEmi.setLoanAccount(loanAcc);
				loanEmiDetailsRepository.saveAndFlush(loanEmi);

				return "Loan processing done successfully...";
			}
		}

		return "Loan processing failed ... ";
	}

	public LoanEmiDetails diductEmi(LoanAccount loanAccount) {

		List<LoanEmiDetails> emiDetails = loanEmiDetailsRepository.findByLoanAccount(loanAccount);
		float remainingLoanBal = loanAccount.getLoanRequested();
		LoanEmiDetails emiToUpdate = null;

		if (Optional.ofNullable(emiDetails).isPresent() && !emiDetails.isEmpty()) {
			// Date maxDate =
			// emiDetails.stream().map(LoanEmiDetails::getPaidOn).max(Date::compareTo).get();

			Optional<LoanEmiDetails> loanEmilDtOptional = emiDetails.stream()
					.max(Comparator.comparing(LoanEmiDetails::getPaidOn));

			if (loanEmilDtOptional.isPresent()) {
				LoanEmiDetails loanEmiDetails = loanEmilDtOptional.get();
				remainingLoanBal = loanEmiDetails.getRemainingLoanAmt();
			}

			float emiToPay = 0f;
			if (remainingLoanBal > 0) {
				if (remainingLoanBal > loanAccount.getEmi()) {
					emiToPay = loanAccount.getEmi();
					remainingLoanBal = remainingLoanBal - emiToPay;
				} else {
					emiToPay = remainingLoanBal;
					remainingLoanBal = 0;
				}
			}

			if (remainingLoanBal == 0) {
				loanAccount.setStatus("close");
				loanAccountRepository.saveAndFlush(loanAccount);
			}

			emiToUpdate = new LoanEmiDetails();
			emiToUpdate.setLoanAccount(loanAccount);
			emiToUpdate.setEmiPaid(emiToPay);
			emiToUpdate.setPaidOn(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
			emiToUpdate.setRemainingLoanAmt(remainingLoanBal);

			emiToUpdate = loanEmiDetailsRepository.saveAndFlush(emiToUpdate);

		}

		return emiToUpdate;

	}

	@Override
	public EmployeeLoanDetails viewLoanDetails(String empId) {

		EmployeeLoanDetails loanDetails = new EmployeeLoanDetails();

		Employee emp = getEmployee(Integer.valueOf(empId));

		if (Optional.ofNullable(emp).isPresent()) {

			List<LoanAccount> loanAccountsList = loanAccountRepository.findByEmployee(emp);
			List<LoanAccountDTO> loanAccountListsDTO = new ArrayList<>();
			if (Optional.ofNullable(loanAccountsList).isPresent() && !loanAccountsList.isEmpty()) {
				loanAccountsList.stream().forEach(loan -> {
					List<LoanEmiDetails> emiDetails = loanEmiDetailsRepository.findByLoanAccount(loan);
					List<LoanEmiDetailsDTO> emiDetailsDTO = null;
					if (Optional.ofNullable(emiDetails).isPresent() && !emiDetails.isEmpty()) {
						emiDetailsDTO = emiDetails.stream().map(emi -> new LoanEmiDetailsDTO(emi.getEmiPaid(),
								emi.getPaidOn().toString(), emi.getRemainingLoanAmt())).collect(Collectors.toList());
					}
					
					Optional<LoanEmiDetails> loanEmilDtOptional = emiDetails.stream()
							.max(Comparator.comparing(LoanEmiDetails::getPaidOn));
					
					float remainingLoanAmt = 0f;
					if(loanEmilDtOptional.isPresent()) {
						LoanEmiDetails emi = loanEmilDtOptional.get();
						remainingLoanAmt = emi.getRemainingLoanAmt();
					}
					
					loanAccountListsDTO.add(new LoanAccountDTO(loan.getLoanId(), loan.getLoanRequested(),
							loan.getRateOfInterest(), loan.getEmi(), loan.getStatus(), remainingLoanAmt, emiDetailsDTO));
				});
				loanDetails.setEmpId(empId);
				loanDetails.setLoanAccounts(loanAccountListsDTO);
			}
		}

		return loanDetails;
	}

}
