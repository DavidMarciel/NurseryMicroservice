package com.nursery.development.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nursery.hospital.communications.repositories.HospitalRepository;
import com.nursery.model.Hospital;

public class HospitalInitialization {

	public ResponseEntity initHospitalsDatabase(HospitalRepository hospitalRepository) {
		
		List<Hospital> hospitals = new ArrayList<>();	
		
		hospitals.add( new Hospital("Hospital 1", "t: 1234", "http://h1.com", 2020.0, 120.0));
		hospitals.add( new Hospital("Hospital 2", "t: 234", "http://h2.com", 121.0, 120.0));
		hospitals.add( new Hospital("Hospital 3", "t: 3456", "http://h3.com", 120.0, 119.0));
		hospitals.add( new Hospital("Hospital 4", "t: 3456", "http://h4.com", 21.20, 19.0));
		hospitals.add( new Hospital("Hospital 5", "t: 3456", "http://h5.com", 22.0, 19.0));
		hospitals.add( new Hospital("Hospital 6", "t: 3456", "http://h6.com", 123.30, 119.0));
		                                                                                
		hospitalRepository.saveAll(hospitals);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
