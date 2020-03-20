package com.batch.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.batch.springboot.demo.entity.RateOfInterest;

@Repository
public interface RoiRepository extends JpaRepository<RateOfInterest, Integer>{
	
	@Query("from RateOfInterest WHERE :salary between salaryFrom and salaryTo")
	public RateOfInterest findByRoiBetweenLoanFromAndLoanTo(double salary);

}
