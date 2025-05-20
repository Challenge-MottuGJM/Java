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

import io.swagger.v3.oas.annotations.Operation;

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
	
	@Operation(description = "Retorna lista de VagaDTO de forma paginada", 
			summary = "Retorna páginas de VagaDTO",
			tags = "Retorno de informação")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<VagaDTO>> retornarVagasPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<VagaDTO> vagas_paginadas = servV.paginar(req);
		
		return ResponseEntity.ok(vagas_paginadas);
	}
	
	
	@Operation(description = "Retorna todos os vagas",
			summary = "Retorna todos os vagas",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas")
	public List<VagaDTO> retornaTodosVagas(){
		
		return repV.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna todos os vagas existentes no Cache",
			summary = "Retorna todos os vagas utilizando Caching",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas_cacheable")
	public List<VagaDTO> retonaTodosVagasCacheable(){
		
		return cacheV.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna um vaga com base em um ID",
			summary = "Retorna um vaga com base em um ID",
			tags = "Retorno de informação")
	@GetMapping(value = "/{id}")
	public VagaDTO retornaVagaPorId(@PathVariable Long id) {
		
		Optional<Vaga> op = cacheV.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Esta operação possibilita a inserção de um novo item na tabela de vagas",
			summary = "Inserir um novo vaga",
			tags = "Inserção de informações")
	@PostMapping(value = "/inserir")
	public Vaga inserirVaga(@RequestBody Vaga vaga) {
		
		repV.save(vaga);
		cacheV.limparCache();
				
		return vaga;
	}
	
	@Operation(description = "Esta operação possibilita a atualização de um item na tabela de vagas",
			summary = "Atualiza um novo vaga",
			tags = "Inserção de informações")
	@PutMapping(value = "/atualizar/{id}")
	public Vaga atualizarVaga(@PathVariable Long id, @RequestBody Vaga vaga) {
		
		Optional<Vaga> op = cacheV.findById(id);
		
		if(op.isPresent()) {
			Vaga vaga_antigo = op.get();
			vaga_antigo.setNumero_vaga(vaga.getNumero_vaga());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return vaga;
	}
	
	@Operation(description = "Esta operação possibilita a exclusão de um item na tabela de vagas",
			summary = "Exclui um novo vaga",
			tags = "Remoção de informações")
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
