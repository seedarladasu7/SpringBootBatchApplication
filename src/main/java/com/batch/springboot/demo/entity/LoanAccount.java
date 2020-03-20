package com.batch.springboot.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "loan_account")
@Data
public class LoanAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private Integer loanId;
	
	@Column(name = "emp_id")
	private Integer empId;
	
	@Column(name = "loan_requested")
	private Double loanRequested;
	
	@Column(name = "rate_of_interest")
	private Double rateOfInterest;
	
	@Column(name = "emi")
	private Double emi;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "emp_id", insertable = false, updatable = false)
	@JsonBackReference
	private Employee employee;
	
	@OneToMany(mappedBy = "loanAccount")
	private List<LoanEmiDetails> loanEmiDetails;	
	
	

}
