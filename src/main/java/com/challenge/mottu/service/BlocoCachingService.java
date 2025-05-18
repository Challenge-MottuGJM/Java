package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.repository.BlocoRepository;

@Service
public class BlocoCachingService {

	@Autowired
	private BlocoRepository repB;
	
	@Cacheable(value = "buscaTodosOsBlocos")
	public List<Bloco> findAll(){
		
		return repB.findAll();
	}
	
	@Cacheable(value = "buscaBlocoPorId", key = "#valor")
	public Optional<Bloco> findById(Long id) {
		return repB.findById(id);
	}
	
	@Cacheable(value = "buscaBlocoPaginado", key = "#valor")
	public Page<Bloco> findAll(PageRequest req){
		return repB.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodosOsBlocos", "buscaBlocoPorId",
						"buscaBlocoPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}
}
