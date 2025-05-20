package com.challenge.mottu.mapper;

import org.springframework.stereotype.Component;

import com.challenge.mottu.dto.PatioDTO;
import com.challenge.mottu.model.Patio;

@Component
public class PatioMapper {
	
	public PatioDTO toDTO(Patio patio) {
		
		PatioDTO dto = new PatioDTO();
		
		dto.setId(patio.getId());
		dto.setAndar(patio.getAndar());
		dto.setNumero_patio(patio.getNumero_patio());
		
		return dto;
	}

}
