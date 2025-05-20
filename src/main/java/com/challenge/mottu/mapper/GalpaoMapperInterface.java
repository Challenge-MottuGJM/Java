package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.GalpaoDTO;
import com.challenge.mottu.model.Galpao;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GalpaoMapperInterface {

	GalpaoDTO toDTO(Galpao galpao);
	
	Galpao toEntity(GalpaoDTO dto);
	
}
