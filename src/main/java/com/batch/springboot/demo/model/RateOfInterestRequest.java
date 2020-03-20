package com.batch.springboot.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class RateOfInterestRequest {
	
	private List<RoiRequest> reteOfInterests;

}
