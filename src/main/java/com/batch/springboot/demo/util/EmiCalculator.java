package com.batch.springboot.demo.util;

public class EmiCalculator {

	public static float calculateEmi(float principal, float interestRate, int duration) {

		interestRate = interestRate / (12 * 100);
		duration = duration * 12;
		return (principal * interestRate * (float) Math.pow(1 + interestRate, duration))
				/ (float) (Math.pow(1 + interestRate, duration) - 1);

	}

}
