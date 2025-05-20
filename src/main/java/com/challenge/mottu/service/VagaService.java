package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.mottu.dto.VagaDTO;
import com.challenge.mottu.mapper.BlocoMapper;
import com.challenge.mottu.mapper.VagaMapperInterface;
import com.challenge.mottu.model.Vaga;
import com.challenge.mottu.repository.VagaRepository;

@Service
public class VagaService {

	@Autowired
	private VagaRepository repV;
	
	@Autowired
	private VagaCachingService cacheV;
	
	@Autowired
	private BlocoMapper mapper;
	
	@Autowired
	private VagaMapperInterface mapperInterface;
	
	@Transactional(readOnly = true)
	public Page<VagaDTO> paginar(PageRequest req){
		
		Page<Vaga> blocos = cacheV.findAll(req);
	
		return blocos.map(i -> mapperInterface.toDTO(i));
	}
}
