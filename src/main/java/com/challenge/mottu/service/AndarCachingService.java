package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.repository.AndarRepository;

@Service
public class AndarCachingService {

	@Autowired
	private AndarRepository repA;
	
	@Cacheable(value = "buscaTodosOsAndares")
	public List<Andar> findAll(){
		
		return repA.findAll();
	}
	
	@Cacheable(value = "buscaAndarPorId")
	public Optional<Andar> findById(Long id) {
		return repA.findById(id);
	}
	
	@Cacheable(value = "buscaAndarPaginado")
	public Page<Andar> findAll(PageRequest req){
		return repA.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodosOsAndares", "buscaAndarPorId",
						"buscaAndarPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}
}
