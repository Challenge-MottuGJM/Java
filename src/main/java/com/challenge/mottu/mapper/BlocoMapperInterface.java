package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.BlocoDTO;
import com.challenge.mottu.model.Bloco;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BlocoMapperInterface {

	BlocoDTO toDTO(Bloco bloco);
	
	Bloco toEntity(BlocoDTO toDTO);
	
}
