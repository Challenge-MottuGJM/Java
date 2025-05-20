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
import com.challenge.mottu.model.Patio;
import com.challenge.mottu.repository.PatioRepository;

@Service
public class PatioCachingService {
	
	@Autowired
	private PatioRepository repP;
	
	@Cacheable(value = "buscaTodosOsPatios")
	public List<Patio> findAll(){
		
		return repP.findAll();
	}
	
	@Cacheable(value = "buscaPatioPorId")
	public Optional<Patio> findById(Long id) {
		return repP.findById(id);
	}
	
	@Cacheable(value = "buscaPatioPaginado")
	public Page<Patio> findAll(PageRequest req){
		return repP.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodosOsPatios", "buscaPatioPorId",
						"buscaPatioPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}

}
