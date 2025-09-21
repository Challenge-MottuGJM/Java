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
import com.challenge.mottu.dto.AndarDTO;
import com.challenge.mottu.mapper.AndarMapperInterface;
import com.challenge.mottu.model.Andar;
import com.challenge.mottu.repository.AndarRepository;
import com.challenge.mottu.service.AndarCachingService;
import com.challenge.mottu.service.AndarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/andares")
public class AndarController {

private final MottuApplication mottuApplication;
	
	@Autowired
	private AndarRepository repA;
	
	@Autowired
	private AndarService servA;
	
	@Autowired
	private AndarCachingService cacheA;
	
	@Autowired
	private AndarMapperInterface mapperInterface;
	
	AndarController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}

	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<AndarDTO>> retornarAndaresPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<AndarDTO> andares_paginados = servA.paginar(req);
		
		return ResponseEntity.ok(andares_paginados);
	}
	
	@GetMapping(value = "/todas")
	public List<AndarDTO> retornaTodosGalpoes(){
		
		return repA.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/todas_cacheable")
	public List<AndarDTO> retonaTodosAndaresCacheable(){
		
		return cacheA.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
		
	}
	
	@GetMapping(value = "/{id}")
	public AndarDTO retornaAndarPorId(@PathVariable Long id) {
		
		Optional<Andar> op = cacheA.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/inserir")
	public ResponseEntity<Andar> inserirAndar(@RequestBody @Valid Andar andar) {
		
		repA.save(andar);
		cacheA.limparCache();
				
		return ResponseEntity.ok(andar);
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public Andar atualizarAndar(@PathVariable Long id, @RequestBody Andar andar) {
		
		Optional<Andar> op = cacheA.findById(id);
		
		if(op.isPresent()) {
			Andar andar_antigo = op.get();
			andar_antigo.setNumero_andar(andar.getNumero_andar());
			
			repA.save(andar_antigo);
			cacheA.limparCache();
			
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return andar;
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public Andar excluirAndar(@PathVariable Long id) {
		
		Optional<Andar> op = repA.findById(id);
		
		if (op.isPresent()) {
			Andar andar_remover = op.get();
			repA.delete(andar_remover);
			cacheA.limparCache();	
			return andar_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
