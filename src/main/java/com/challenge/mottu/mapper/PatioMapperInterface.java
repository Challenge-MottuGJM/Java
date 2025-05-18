package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.PatioDTO;
import com.challenge.mottu.model.Patio;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PatioMapperInterface {

	PatioDTO toDTO(Patio patio);
	
	Patio toEntity(PatioDTO toDTO);
	
}
