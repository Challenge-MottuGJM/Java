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
import com.challenge.mottu.dto.GalpaoDTO;
import com.challenge.mottu.mapper.GalpaoMapperInterface;
import com.challenge.mottu.model.Galpao;
import com.challenge.mottu.repository.GalpaoRepository;
import com.challenge.mottu.service.GalpaoCachingService;
import com.challenge.mottu.service.GalpaoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/galpoes")
public class GalpaoController {

	private final MottuApplication mottuApplication;
	
	@Autowired
	private GalpaoRepository repG;
	
	@Autowired
	private GalpaoService servG;
	
	@Autowired
	private GalpaoCachingService cacheG;
	
	@Autowired
	private GalpaoMapperInterface mapperInterface;
	
	GalpaoController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}
	
	@Operation(description = "Retorna lista de GalpaoDTO de forma paginada", 
			summary = "Retorna páginas de GalpaoDTO",
			tags = "Retorno de informação")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<GalpaoDTO>> retornarGalpoesPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<GalpaoDTO> galpoes_paginados = servG.paginar(req);
		
		return ResponseEntity.ok(galpoes_paginados);
	}
	
	
	@Operation(description = "Retorna todos os galpões",
			summary = "Retorna todos os Galpões",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas")
	public List<GalpaoDTO> retornaTodosGalpoes(){
		
		return repG.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna todos os galpões existentes no Cache",
			summary = "Retorna todos os Galpões utilizando Caching",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas_cacheable")
	public List<GalpaoDTO> retonaTodosGalpoesCacheable(){
		
		return cacheG.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna um galpão com base em um ID",
			summary = "Retorna um galpão com base em um ID",
			tags = "Retorno de informação")
	@GetMapping(value = "/{id}")
	public GalpaoDTO retornaGalpaoPorId(@PathVariable Long id) {
		
		Optional<Galpao> op = cacheG.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Esta operação possibilita a inserção de um novo item na tabela de galpões",
			summary = "Inserir um novo galpão",
			tags = "Inserção de informações")
	@PostMapping(value = "/inserir")
	public ResponseEntity<Galpao> inserirGalpao(@RequestBody @Valid Galpao galpao) {
		
		repG.save(galpao);
		cacheG.limparCache();
				
		return ResponseEntity.ok(galpao);
	}
	
	@Operation(description = "Esta operação possibilita a atualização de um item na tabela de galpões",
			summary = "Atualiza um novo galpão",
			tags = "Inserção de informações")
	@PutMapping(value = "/atualizar/{id}")
	public Galpao atualizarGalpao(@PathVariable Long id, @RequestBody Galpao galpao) {
		
		Optional<Galpao> op = cacheG.findById(id);
		
		if(op.isPresent()) {
			Galpao galpao_antigo = op.get();
			galpao_antigo.setNome_galpao(galpao.getNome_galpao());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return galpao;
	}
	
	@Operation(description = "Esta operação possibilita a exclusão de um item na tabela de galpões",
			summary = "Exclui um novo galpão",
			tags = "Remoção de informações")
	@DeleteMapping(value = "/excluir/{id}")
	public Galpao excluirGalpao(@PathVariable Long id) {
		
		Optional<Galpao> op = repG.findById(id);
		
		if (op.isPresent()) {
			Galpao galpao_remover = op.get();
			repG.delete(galpao_remover);
			cacheG.limparCache();	
			return galpao_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
}
