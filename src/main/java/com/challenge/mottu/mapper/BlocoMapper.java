package com.challenge.mottu.mapper;

import org.springframework.stereotype.Component;

import com.challenge.mottu.dto.BlocoDTO;
import com.challenge.mottu.model.Bloco;

@Component
public class BlocoMapper {
	
public BlocoDTO toDTO(Bloco bloco) {
		
		BlocoDTO dto = new BlocoDTO();
		
		dto.setId(bloco.getId());
		dto.setPatio(bloco.getPatio());
		dto.setLetra_bloco(bloco.getLetra_bloco());
		
		return dto;
	}

}
