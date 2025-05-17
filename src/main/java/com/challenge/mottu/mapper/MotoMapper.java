package com.challenge.mottu.mapper;

import com.challenge.mottu.dto.MotoDTO;
import com.challenge.mottu.model.Moto;

public class MotoMapper {

	public MotoDTO toDTO(Moto moto) {
		
		MotoDTO dto = new MotoDTO();
		
		dto.setId(moto.getId());
		dto.setVaga(moto.getVaga());
		dto.setStatus(moto.getStatus());
		dto.setModelo(moto.getModelo());
		dto.setMarca(moto.getMarca());
		dto.setPlaca(moto.getPlaca());
		dto.setChassi(moto.getChassi());
		
		return dto;
	}
	
}
