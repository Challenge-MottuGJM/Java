package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.mottu.dto.BlocoDTO;
import com.challenge.mottu.mapper.BlocoMapper;
import com.challenge.mottu.mapper.BlocoMapperInterface;
import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.repository.BlocoRepository;

@Service
public class BlocoService {
	
	@Autowired
	private BlocoRepository repB;
	
	@Autowired
	private BlocoCachingService cacheB;
	
	@Autowired
	private BlocoMapper mapper;
	
	@Autowired
	private BlocoMapperInterface mapperInterface;
	
	@Transactional(readOnly = true)
	public Page<BlocoDTO> paginar(PageRequest req){
		
		Page<Bloco> blocos = cacheB.findAll(req);
	
		return blocos.map(i -> mapperInterface.toDTO(i));
	}

}
