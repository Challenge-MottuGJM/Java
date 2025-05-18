package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Galpao;
import com.challenge.mottu.repository.GalpaoRepository;

@Service
public class GalpaoCachingService {

	@Autowired
	private GalpaoRepository repG;
	
	@Cacheable(value = "buscaTodosOsGalpoes")
	public List<Galpao> findAll(){
		
		return repG.findAll();
	}
	
	@Cacheable(value = "buscaGalpaoPorId", key = "#valor")
	public Optional<Galpao> findById(Long id) {
		return repG.findById(id);
	}
	
	@Cacheable(value = "buscaGalpaoPaginado", key = "#valor")
	public Page<Galpao> findAll(PageRequest req){
		return repG.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodosOsGalpoes", "buscaGalpaoPorId",
						"buscaGalpaoPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}
}
