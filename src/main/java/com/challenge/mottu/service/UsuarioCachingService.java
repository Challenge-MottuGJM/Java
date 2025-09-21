package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.UsuarioRepository;

@Service
public class UsuarioCachingService {

	@Autowired
	private UsuarioRepository repU;
	
	@Cacheable(value = "buscaTodosOsUsuarios")
	public List<Usuario> findAll(){
		
		return repU.findAll();
	}
	
	@Cacheable(value = "buscaUsuarioPorId")
	public Optional<Usuario> findById(Long id) {
		return repU.findById(id);
	}
	
	@Cacheable(value = "buscaUsuarioPaginado")
	public Page<Usuario> findAll(PageRequest req){
		return repU.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodosOsUsuarios", "buscaUsuarioPorId",
						"buscaUsuarioPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}
}
