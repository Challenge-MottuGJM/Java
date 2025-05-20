package com.challenge.mottu.mapper;

import org.springframework.stereotype.Component;

import com.challenge.mottu.dto.AndarDTO;
import com.challenge.mottu.model.Andar;

@Component
public class AndarMapper {
	
	public AndarDTO toDTO(Andar andar) {
		
		AndarDTO dto = new AndarDTO();
		
		dto.setId(andar.getId());
		dto.setGalpao(andar.getGalpao());
		dto.setNumero_andar(andar.getNumero_andar());
		
		return dto;
	}

}
