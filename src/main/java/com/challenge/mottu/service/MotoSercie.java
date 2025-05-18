package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.dto.MotoDTO;
import com.challenge.mottu.mapper.BlocoMapper;
import com.challenge.mottu.mapper.MotoMapperInterface;
import com.challenge.mottu.model.Moto;
import com.challenge.mottu.repository.MotoRepository;

@Service
public class MotoSercie {

	@Autowired
	private MotoRepository repM;
	
	@Autowired
	private MotoCachingService cacheM;
	
	@Autowired
	private BlocoMapper mapper;
	
	@Autowired
	private MotoMapperInterface mapperInterface;
	
	
	public Page<MotoDTO> paginar(PageRequest req){
		
		Page<Moto> motos = cacheM.findAll(req);
	
		return motos.map(i -> mapperInterface.toDTO(i));
	}
}
