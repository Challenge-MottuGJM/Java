package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.dto.GalpaoDTO;
import com.challenge.mottu.mapper.GalpaoMapper;
import com.challenge.mottu.mapper.GalpaoMapperInterface;
import com.challenge.mottu.model.Galpao;
import com.challenge.mottu.repository.GalpaoRepository;

@Service
public class GalpaoService {

	@Autowired
	private GalpaoRepository repG;
	
	@Autowired
	private GalpaoCachingService cacheG;
	
	@Autowired
	private GalpaoMapper mapper;
	
	@Autowired
	private GalpaoMapperInterface mapperInterface;
	
	
	public Page<GalpaoDTO> paginar(PageRequest req){
		
		Page<Galpao> galpoes = cacheG.findAll(req);
	
		return galpoes.map(i -> mapperInterface.toDTO(i));
	}
}
