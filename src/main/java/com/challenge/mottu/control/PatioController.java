package com.challenge.mottu.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.mottu.MottuApplication;
import com.challenge.mottu.dto.PatioDTO;
import com.challenge.mottu.mapper.PatioMapperInterface;
import com.challenge.mottu.model.Patio;
import com.challenge.mottu.repository.PatioRepository;
import com.challenge.mottu.service.PatioCachingService;
import com.challenge.mottu.service.PatioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/patios")
public class PatioController {
	
	private final MottuApplication mottuApplication;
	
	@Autowired
	private PatioRepository repP;
	
	@Autowired
	private PatioService servP;
	
	@Autowired
	private PatioCachingService cacheP;
	
	@Autowired
	private PatioMapperInterface mapperInterface;
	
	PatioController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}
	
	@Operation(description = "Retorna lista de PatioDTO de forma paginada", 
			summary = "Retorna páginas de PatioDTO",
			tags = "Retorno de informação")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<PatioDTO>> retornarPatiosPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<PatioDTO> patios_paginados = servP.paginar(req);
		
		return ResponseEntity.ok(patios_paginados);
	}
	
	
	@Operation(description = "Retorna todos os aptios",
			summary = "Retorna todos os patios",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas")
	public List<PatioDTO> retornaTodosPatios(){
		
		List<Patio> patios = repP.findAll();
		List<PatioDTO> patios_dto = new ArrayList<PatioDTO>();
		
		for (Patio p : patios) {
			patios_dto.add(mapperInterface.toDTO(p));
		}
		
		return patios_dto;
	}
	
	@Operation(description = "Retorna todos os patios existentes no Cache",
			summary = "Retorna todos os patios utilizando Caching",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas_cacheable")
	public List<PatioDTO> retonaTodosPatiosCacheable(){
		
		List<Patio> patios = cacheP.findAll();
		List<PatioDTO> patios_dto = new ArrayList<PatioDTO>();
		
		for (Patio p : patios) {
			patios_dto.add(mapperInterface.toDTO(p));
		}
		
		return patios_dto;
		
	}
	
	@Operation(description = "Retorna um patio com base em um ID",
			summary = "Retorna um patio com base em um ID",
			tags = "Retorno de informação")
	@GetMapping(value = "/{id}")
	public PatioDTO retornaPatioPorId(@PathVariable Long id) {
		
		Optional<Patio> op = cacheP.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Esta operação possibilita a inserção de um novo item na tabela de patios",
			summary = "Inserir um novo patio",
			tags = "Inserção de informações")
	@PostMapping(value = "/inserir")
	public Patio inserirPatio(@RequestBody Patio patio) {
		
		repP.save(patio);
		cacheP.limparCache();
				
		return patio;
	}
	
	@Operation(description = "Esta operação possibilita a atualização de um item na tabela de patios",
			summary = "Atualiza um novo patio",
			tags = "Inserção de informações")
	@PutMapping(value = "/atualizar/{id}")
	public Patio atualizarPatio(@PathVariable Long id, @RequestBody Patio patio) {
		
		Optional<Patio> op = cacheP.findById(id);
		
		if(op.isPresent()) {
			Patio patio_antigo = op.get();
			patio_antigo.setNumero_patio(patio.getNumero_patio());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return patio;
	}
	
	@Operation(description = "Esta operação possibilita a exclusão de um item na tabela de patios",
			summary = "Exclui um novo patio",
			tags = "Remoção de informações")
	@DeleteMapping(value = "/excluir/{id}")
	public Patio excluirPatio(@PathVariable Long id) {
		
		Optional<Patio> op = repP.findById(id);
		
		if (op.isPresent()) {
			Patio patio_remover = op.get();
			repP.delete(patio_remover);
			cacheP.limparCache();	
			return patio_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
