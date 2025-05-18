package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.AndarDTO;
import com.challenge.mottu.model.Andar;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AndarMapperInterface {
	
	AndarDTO toDTO(Andar andar);
	
	Andar toEntity(AndarDTO toDTO);

}
