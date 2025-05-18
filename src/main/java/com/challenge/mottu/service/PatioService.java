package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.dto.PatioDTO;
import com.challenge.mottu.mapper.PatioMapper;
import com.challenge.mottu.mapper.PatioMapperInterface;
import com.challenge.mottu.model.Patio;
import com.challenge.mottu.repository.PatioRepository;

@Service
public class PatioService {

	@Autowired
	private PatioRepository repP;
	
	@Autowired
	private PatioCachingService cacheP;
	
	@Autowired
	private PatioMapper mapper;
	
	@Autowired
	private PatioMapperInterface mapperInterface;

public Page<PatioDTO> paginar(PageRequest req){
		
		Page<Patio> patios = cacheP.findAll(req);
	
		return patios.map(i -> mapperInterface.toDTO(i));
	}
}
