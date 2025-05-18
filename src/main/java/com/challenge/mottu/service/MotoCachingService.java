package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Moto;
import com.challenge.mottu.repository.MotoRepository;

@Service
public class MotoCachingService {
	
	@Autowired
	private MotoRepository repM;
	
	@Cacheable(value = "buscaTodasAsMotos")
	public List<Moto> findAll(){
		
		return repM.findAll();
	}
	
	@Cacheable(value = "buscaMotoPorId", key = "#valor")
	public Optional<Moto> findById(Long id) {
		return repM.findById(id);
	}
	
	@Cacheable(value = "buscaMotoPaginado", key = "#valor")
	public Page<Moto> findAll(PageRequest req){
		return repM.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodasAsMotos", "buscaMotoPorId",
						"buscaMotoPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}

}
