package com.challenge.mottu.mapper;

import org.springframework.stereotype.Component;

import com.challenge.mottu.dto.UsuarioDTO;
import com.challenge.mottu.model.Usuario;

@Component
public class UsuarioMapper {
	
	public UsuarioDTO toDTO(Usuario usuario) {
		
		UsuarioDTO dto = new UsuarioDTO();
		
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setCargo(usuario.getCargo());
		dto.setSenha(usuario.getSenha());
		dto.setUsername(usuario.getUsername());
		
		return dto;
	}

}
