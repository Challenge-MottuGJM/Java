package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.VagaDTO;
import com.challenge.mottu.model.Vaga;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface VagaMapperInterface {

	VagaDTO toDTO(Vaga vaga);
	
	Vaga toEntity(VagaDTO toDTO);
	
}
