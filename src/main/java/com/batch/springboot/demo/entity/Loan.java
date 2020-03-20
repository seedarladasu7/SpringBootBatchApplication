package com.batch.springboot.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "loan")
@Data
public class Loan {
	
	private Integer id;

}
