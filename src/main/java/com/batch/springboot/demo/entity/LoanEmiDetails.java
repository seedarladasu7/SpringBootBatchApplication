package com.batch.springboot.demo.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "loan_emi_details")
@Data
public class LoanEmiDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_emi_id")
	private Integer loanEmiId;
	
	@Column(name = "emi_paid")
	private Float emiPaid;
	
	@Column(name = "paid_on")
	private Date paidOn;
	
	@Column(name = "remaining_loan_amt")
	private Float remainingLoanAmt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_id", insertable = false, updatable = false)
	@JsonBackReference
	private LoanAccount loanAccount;

}
