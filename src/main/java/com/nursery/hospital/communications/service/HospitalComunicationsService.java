package com.nursery.hospital.communications.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nursery.development.code.HospitalInitialization;
import com.nursery.hospital.communications.repositories.HospitalRepository;
import com.nursery.model.Hospital;
import com.nursery.number.format.CoordenatesMixer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hospitals")
public class HospitalComunicationsService {

	private HospitalRepository hospitalRepository;
	
	@Autowired
	public HospitalComunicationsService( HospitalRepository hospitalRepository) {
			this.hospitalRepository = hospitalRepository;
	}

	public HospitalComunicationsService( ) {
	}

	
	@PostMapping("/fill-database")
	public ResponseEntity<?> initDatabaseOnStartup() {
		
		log.info("       init database");
	    
		HospitalInitialization hospitalInitialization = new HospitalInitialization();
		
		return hospitalInitialization.initHospitalsDatabase(hospitalRepository);
	}

	
	@GetMapping
	public ResponseEntity<List<Hospital>> getAllHospitals() {
		
		Iterable<Hospital> allHospitals = hospitalRepository.findAll();
				
		List<Hospital> hospitals = StreamSupport
			.stream(allHospitals.spliterator(), false)
			.collect(Collectors.toList());

		return new ResponseEntity<List<Hospital>>( hospitals, HttpStatus.OK);
	} 
	
	@GetMapping("/close-hospitals")
	public ResponseEntity<List<Hospital>> getCloserHospitals(@RequestParam(value = "longitude") double longitude, @RequestParam(value = "latitude") double latitude) {
		
		// We dont have to sanitize the inputs since they are not objects they would raise a cast exception if they dont match the type
		// in case of receive an string we should check for sql injection and prevent it sanitizing it
//		Sanitizer sanitizer = new Sanitizer();
//		sanitizer.sanitizeString(longitude);
//		sanitizer.sanitizeString(latitude);
		
		CoordenatesMixer coordenatesMixer = new CoordenatesMixer();
		CoordenatesMixer.AllowedRangeInterval allowedRange = coordenatesMixer.getMergeBounds(longitude, latitude, 100.00);
		
		Iterable<Hospital> allHospitals = hospitalRepository.findByCoordenatesBetween(allowedRange.getMinRange(), allowedRange.getMaxRange());
				
		List<Hospital> hospitals = StreamSupport
			.stream(allHospitals.spliterator(), false)
			.collect(Collectors.toList());
		
		return new ResponseEntity<List<Hospital>>( hospitals, HttpStatus.OK);
	}
}
