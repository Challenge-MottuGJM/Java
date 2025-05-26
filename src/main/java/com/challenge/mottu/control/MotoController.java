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
import com.challenge.mottu.dto.MotoDTO;
import com.challenge.mottu.mapper.MotoMapperInterface;
import com.challenge.mottu.model.Moto;
import com.challenge.mottu.repository.MotoRepository;
import com.challenge.mottu.service.MotoCachingService;
import com.challenge.mottu.service.MotoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/motos")
public class MotoController {
	
private final MottuApplication mottuApplication;
	
	@Autowired
	private MotoRepository repM;
	
	@Autowired
	private MotoService servM;
	
	@Autowired
	private MotoCachingService cacheM;
	
	@Autowired
	private MotoMapperInterface mapperInterface;
	
	MotoController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}
	
	@GetMapping(value = "/moto_por_status")
	public List<Moto> retornaMotoPorStatus(@RequestParam(value = "status") String status){
		
		return repM.retornaMotosPorStatus(status);
		
	}
	
	@GetMapping(value = "/moto_por_modelo")
	public List<Moto> retornaMotoPorModelo(@RequestParam(value = "modelo") String modelo){
		
		return repM.retornaMotosPorModelo(modelo);
		
	}
	
	@GetMapping(value = "/moto_por_placa")
	public List<Moto> retornaMotoPorPlaca(@RequestParam(value = "placa") String placa){
		
		return repM.retornaMotosPorPlaca(placa);
		
	}
	
	@Operation(description = "Retorna lista de MotoDTO de forma paginada", 
			summary = "Retorna páginas de MotoDTO",
			tags = "Retorno de informação")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<MotoDTO>> retornarMotosPaginadas(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<MotoDTO> motos_paginadas = servM.paginar(req);
		
		return ResponseEntity.ok(motos_paginadas);
	}
	
	@Operation(description = "Retorna todos as motos",
			summary = "Retorna todos as motos",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas")
	public List<MotoDTO> retornaMotosGalpoes(){
		
		return repM.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}
	
	@Operation(description = "Retorna todos as motos existentes no Cache",
			summary = "Retorna todos as motos utilizando Caching",
			tags = "Retorno de informação")
	@GetMapping(value = "/todas_cacheable")
	public List<MotoDTO> retonaTodasMotosCacheable(){
		
		return cacheM.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
		
	}
	
	@Operation(description = "Retorna uma moto com base em um ID",
			summary = "Retorna uma moto com base em um ID",
			tags = "Retorno de informação")
	@GetMapping(value = "/{id}")
	public MotoDTO retornaMotoPorId(@PathVariable Long id) {
		
		Optional<Moto> op = cacheM.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Esta operação possibilita a inserção de um novo item na tabela de motos",
			summary = "Inserir uma nova moto",
			tags = "Inserção de informações")
	@PostMapping(value = "/inserir")
	public ResponseEntity<Moto> inserirMoto(@RequestBody @Valid Moto moto) {
		
		repM.save(moto);
		cacheM.limparCache();
				
		return ResponseEntity.ok(moto);
	}
	
	@Operation(description = "Esta operação possibilita a atualização de um item na tabela de motos",
			summary = "Atualiza uma nova moto",
			tags = "Inserção de informações")
	@PutMapping(value = "/atualizar/{id}")
	public Moto atualizarMoto(@PathVariable Long id, @RequestBody Moto moto) {
		
		Optional<Moto> op = cacheM.findById(id);
		
		if(op.isPresent()) {
			Moto moto_antiga = op.get();
			moto_antiga.setModelo(moto.getModelo());
			moto_antiga.setChassi(moto.getChassi());
			moto_antiga.setMarca(moto.getMarca());
			moto_antiga.setPlaca(moto.getPlaca());
			moto_antiga.setStatus(moto.getStatus());
			moto_antiga.setVaga(moto.getVaga());
			
			repM.save(moto_antiga);
			cacheM.limparCache();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return moto;
	}
	
	@Operation(description = "Esta operação possibilita a exclusão de um item na tabela de motos",
			summary = "Exclui uma nova moto",
			tags = "Remoção de informações")
	@DeleteMapping(value = "/excluir/{id}")
	public Moto excluirMoto(@PathVariable Long id) {
		
		Optional<Moto> op = repM.findById(id);
		
		if (op.isPresent()) {
			Moto moto_remover = op.get();
			repM.delete(moto_remover);
			cacheM.limparCache();	
			return moto_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
