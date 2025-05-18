package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.MotoDTO;
import com.challenge.mottu.model.Moto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MotoMapperInterface {
	
	MotoDTO toDTO(Moto moto);
	
	Moto toEntity(MotoDTO toDTO);
}
