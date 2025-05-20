package com.challenge.mottu.mapper;

import org.springframework.stereotype.Component;

import com.challenge.mottu.dto.GalpaoDTO;
import com.challenge.mottu.model.Galpao;

@Component
public class GalpaoMapper {

	public GalpaoDTO toDTO(Galpao galpao) {
		
		GalpaoDTO dto = new GalpaoDTO();
		
		dto.setId(galpao.getId());
		dto.setNome_galpao(galpao.getNome_galpao());
		
		return dto;
		
	}
}
