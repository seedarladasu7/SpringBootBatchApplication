package com.batch.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "emi_rates")
@Data
public class EmiRates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emi_rate_id")
	private Integer emiRateId;
	
	@Column(name = "loan_from")
	private double loanFrom;
	
	@Column(name = "loan_to")
	private double loanTo;
	
	@Column(name = "rate_of_interest")
	private double rateOfInterest;

}
