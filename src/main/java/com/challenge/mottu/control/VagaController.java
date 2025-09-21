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
import com.challenge.mottu.dto.VagaDTO;
import com.challenge.mottu.mapper.VagaMapperInterface;
import com.challenge.mottu.model.Vaga;
import com.challenge.mottu.repository.VagaRepository;
import com.challenge.mottu.service.VagaCachingService;
import com.challenge.mottu.service.VagaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/vagas")
public class VagaController {

private final MottuApplication mottuApplication;
	
	@Autowired
	private VagaRepository repV;
	
	@Autowired
	private VagaService servV;
	
	@Autowired
	private VagaCachingService cacheV;
	
	@Autowired
	private VagaMapperInterface mapperInterface;
	
	VagaController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}
	
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<VagaDTO>> retornarVagasPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<VagaDTO> vagas_paginadas = servV.paginar(req);
		
		return ResponseEntity.ok(vagas_paginadas);
	}
	
	@GetMapping(value = "/todas")
	public List<VagaDTO> retornaTodosVagas(){
		
		return repV.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}

	@GetMapping(value = "/todas_cacheable")
	public List<VagaDTO> retonaTodosVagasCacheable(){
		
		return cacheV.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/{id}")
	public VagaDTO retornaVagaPorId(@PathVariable Long id) {
		
		Optional<Vaga> op = cacheV.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/inserir")
	public ResponseEntity<Vaga> inserirVaga(@RequestBody @Valid Vaga vaga) {
		
		repV.save(vaga);
		cacheV.limparCache();
				
		return ResponseEntity.ok(vaga);
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public Vaga atualizarVaga(@PathVariable Long id, @RequestBody Vaga vaga) {
		
		Optional<Vaga> op = cacheV.findById(id);
		
		if(op.isPresent()) {
			Vaga vaga_antiga = op.get();
			vaga_antiga.setNumero_vaga(vaga.getNumero_vaga());
			
			repV.save(vaga_antiga);
			cacheV.limparCache();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return vaga;
	}

	@DeleteMapping(value = "/excluir/{id}")
	public Vaga excluirVaga(@PathVariable Long id) {
		
		Optional<Vaga> op = repV.findById(id);
		
		if (op.isPresent()) {
			Vaga vaga_remover = op.get();
			repV.delete(vaga_remover);
			cacheV.limparCache();	
			return vaga_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
