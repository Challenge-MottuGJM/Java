package com.challenge.mottu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.challenge.mottu.dto.UsuarioDTO;
import com.challenge.mottu.model.Usuario;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapperInterface {

	UsuarioDTO toDTO(Usuario usuario);
	
	Usuario toEntity(UsuarioDTO toDTO);
}
