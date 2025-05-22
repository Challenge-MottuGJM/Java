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
import com.challenge.mottu.dto.BlocoDTO;
import com.challenge.mottu.mapper.BlocoMapperInterface;
import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.repository.BlocoRepository;
import com.challenge.mottu.service.BlocoCachingService;
import com.challenge.mottu.service.BlocoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/blocos")
public class BlocoController {
	
	private final MottuApplication mottuApplication;

	@Autowired
	private BlocoRepository repB;
	
	@Autowired
	private BlocoService servB;
	
	@Autowired
	private BlocoCachingService cacheB;
	
	@Autowired
	private BlocoMapperInterface mapperInterface;
	
	BlocoController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}
	
	@Operation(description = "Retorna lista de BlocoDTO de forma paginada", 
			summary = "Retorna páginas de BlocoDTO",
			tags = "Retorno de informação")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<BlocoDTO>> retornarBlocosPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<BlocoDTO> blocos_paginados = servB.paginar(req);
		
		return ResponseEntity.ok(blocos_paginados);
	}
	
	
	@Operation(description = "Retorna todos os blocos",
			summary = "Retorna todos os blocos",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas")
	public List<BlocoDTO> retornaTodosBlocos(){
				
		return repB.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna todos os blocos existentes no Cache",
			summary = "Retorna todos os blocos utilizando Caching",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas_cacheable")
	public List<BlocoDTO> retonaTodosBlocosCacheable(){
		
		return cacheB.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna um bloco com base em um ID",
			summary = "Retorna um bloco com base em um ID",
			tags = "Retorno de informação")
	@GetMapping(value = "/{id}")
	public BlocoDTO retornaBlocoPorId(@PathVariable Long id) {
		
		Optional<Bloco> op = cacheB.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Esta operação possibilita a inserção de um novo item na tabela de blocos",
			summary = "Inserir um novo bloco",
			tags = "Inserção de informações")
	@PostMapping(value = "/inserir")
	public ResponseEntity<Bloco> inserirBloco(@RequestBody @Valid Bloco bloco) {
		
		repB.save(bloco);
		cacheB.limparCache();
				
		return ResponseEntity.ok(bloco);
	}
	
	@Operation(description = "Esta operação possibilita a atualização de um item na tabela de blocos",
			summary = "Atualiza um novo bloco",
			tags = "Inserção de informações")
	@PutMapping(value = "/atualizar/{id}")
	public Bloco atualizarBloco(@PathVariable Long id, @RequestBody Bloco bloco) {
		
		Optional<Bloco> op = cacheB.findById(id);
		
		if(op.isPresent()) {
			Bloco bloco_antigo = op.get();
			bloco_antigo.setLetra_bloco(bloco.getLetra_bloco());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return bloco;
	}
	
	@Operation(description = "Esta operação possibilita a exclusão de um item na tabela de blocos",
			summary = "Exclui um novo bloco",
			tags = "Remoção de informações")
	@DeleteMapping(value = "/excluir/{id}")
	public Bloco excluirBloco(@PathVariable Long id) {
		
		Optional<Bloco> op = repB.findById(id);
		
		if (op.isPresent()) {
			Bloco bloco_remover = op.get();
			repB.delete(bloco_remover);
			cacheB.limparCache();	
			return bloco_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
