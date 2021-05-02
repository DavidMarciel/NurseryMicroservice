package com.nursery.hospital.communications.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nursery.model.Hospital;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital, String>{

	public List<Hospital> findByCoordenatesBetween(double coordenates1, double coordenates2);
	
}
