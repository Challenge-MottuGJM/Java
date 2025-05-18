package com.challenge.mottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.challenge.mottu.model.Vaga;
import com.challenge.mottu.repository.VagaRepository;

@Service
public class VagaCachingService {
	
	@Autowired
	private VagaRepository repV;
	
	@Cacheable(value = "buscaTodasAsVagas")
	public List<Vaga> findAll(){
		
		return repV.findAll();
	}
	
	@Cacheable(value = "buscaVagaPorId", key = "#valor")
	public Optional<Vaga> findById(Long id) {
		return repV.findById(id);
	}
	
	@Cacheable(value = "buscaVagaPaginado", key = "#valor")
	public Page<Vaga> findAll(PageRequest req){
		return repV.findAll(req);
	}
	
	@CacheEvict(value = {"buscaTodasAsVagas", "buscaVagaPorId",
						"buscaVagaPaginado"}, allEntries = true)
	public void limparCache() {
		System.out.println("Limpando o cache!");
	}

}
