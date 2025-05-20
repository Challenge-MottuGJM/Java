package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.mottu.dto.AndarDTO;
import com.challenge.mottu.mapper.AndarMapper;
import com.challenge.mottu.mapper.AndarMapperInterface;
import com.challenge.mottu.model.Andar;
import com.challenge.mottu.repository.AndarRepository;

@Service
public class AndarService {

	@Autowired
	private AndarRepository repA;
	
	@Autowired
	private AndarCachingService cacheA;
	
	@Autowired
	private AndarMapper mapper;
	
	@Autowired
	private AndarMapperInterface mapperInterface;
	
	@Transactional(readOnly = true)
	public Page<AndarDTO> paginar(PageRequest req){
		
		Page<Andar> andares = cacheA.findAll(req);
	
		return andares.map(i -> mapperInterface.toDTO(i));
	}
}
