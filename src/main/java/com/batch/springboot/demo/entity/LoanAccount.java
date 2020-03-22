package com.batch.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "loan_account")
@Data
@ToString
public class LoanAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private Integer loanId;
	
	@Column(name = "loan_requested")
	private Float loanRequested;
	
	@Column(name = "rate_of_interest")
	private Float rateOfInterest;
	
	@Column(name = "emi")
	private Float emi;
	
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "emp_id")
	@JsonBackReference
	private Employee employee;
	
}
