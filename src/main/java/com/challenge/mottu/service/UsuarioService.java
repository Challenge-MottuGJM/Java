package com.challenge.mottu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.mottu.dto.UsuarioDTO;
import com.challenge.mottu.mapper.UsuarioMapper;
import com.challenge.mottu.mapper.UsuarioMapperInterface;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private UsuarioCachingService cacheU;
	
	@Autowired
	private UsuarioMapper mapper;
	
	@Autowired
	private UsuarioMapperInterface mapperInterface;
	
	@Transactional(readOnly = true)
	public Page<UsuarioDTO> paginar(PageRequest req){
		
		Page<Usuario> usuarios = cacheU.findAll(req);
	
		return usuarios.map(i -> mapperInterface.toDTO(i));
	}

}
