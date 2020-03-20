package com.batch.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "rate_of_interest")
@Data
@ToString
public class RateOfInterest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emi_rate_id")
	private Integer emiRateId;
	
	@Column(name = "salary_from")
	private double salaryFrom;
	
	@Column(name = "salary_to")
	private double salaryTo;
	
	@Column(name = "roi")
	private double roi;

}
