package com.challenge.mottu.control;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import jakarta.validation.Valid;

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

	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<PatioDTO>> retornarPatiosPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<PatioDTO> patios_paginados = servP.paginar(req);
		
		return ResponseEntity.ok(patios_paginados);
	}
	
	@GetMapping(value = "/todas")
	public List<PatioDTO> retornaTodosPatios(){
		
		return repP.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}

	@GetMapping(value = "/todas_cacheable")
	public List<PatioDTO> retonaTodosPatiosCacheable(){
		
		return cacheP.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	public PatioDTO retornaPatioPorId(@PathVariable Long id) {
		
		Optional<Patio> op = cacheP.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/inserir")
	public ResponseEntity<Patio> inserirPatio(@RequestBody @Valid Patio patio) {
		
		repP.save(patio);
		cacheP.limparCache();
				
		return ResponseEntity.ok(patio);
	}

	@PutMapping(value = "/atualizar/{id}")
	public Patio atualizarPatio(@PathVariable Long id, @RequestBody Patio patio) {
		
		Optional<Patio> op = cacheP.findById(id);
		
		if(op.isPresent()) {
			Patio patio_antigo = op.get();
			patio_antigo.setNumero_patio(patio.getNumero_patio());
			
			repP.save(patio_antigo);
			cacheP.limparCache();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return patio;
	}

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
