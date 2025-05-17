package com.challenge.mottu.mapper;

import com.challenge.mottu.dto.VagaDTO;
import com.challenge.mottu.model.Vaga;

public class VagaMapper {
	
	public VagaDTO toDTO (Vaga vaga) {
		
		VagaDTO dto = new VagaDTO();
		
		dto.setId(vaga.getId());
		dto.setBloco(vaga.getBloco());
		dto.setNumero_vaga(vaga.getNumero_vaga());
		
		return dto;
	}

}
