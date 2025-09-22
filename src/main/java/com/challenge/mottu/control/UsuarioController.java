package com.challenge.mottu.control;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.challenge.mottu.dto.UsuarioDTO;
import com.challenge.mottu.mapper.UsuarioMapperInterface;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.UsuarioRepository;
import com.challenge.mottu.service.UsuarioCachingService;
import com.challenge.mottu.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {
	
	private final MottuApplication mottuApplication;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioService servU;
	
	@Autowired
	private UsuarioCachingService cacheU;	
	
	@Autowired
	private UsuarioMapperInterface mapperInterface;
	
	public UsuarioController (MottuApplication mottuApplication) {
		this.mottuApplication = mottuApplication;
	}

	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<UsuarioDTO>> retornarUsuariosPaginados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "2") Integer size){
		
		PageRequest req = PageRequest.of(page, size);
		
		Page<UsuarioDTO> usuarios_paginados = servU.paginar(req);
		
		return ResponseEntity.ok(usuarios_paginados);
	}
	
	@GetMapping(value = "/todas")
	public List<UsuarioDTO> retornaTodosUsuarios(){
		
		return repU.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
	}

	@GetMapping(value = "/todas_cacheable")
	public List<UsuarioDTO> retonaTodosUsuariosCacheable(){
		
		return cacheU.findAll().stream().map(mapperInterface::toDTO).collect(Collectors.toList());
		
	}

	@GetMapping(value = "/{id}")
	public UsuarioDTO retornaUsuarioPorId(@PathVariable Long id) {
		
		Optional<Usuario> op = cacheU.findById(id);
		
		if(op.isPresent()) {
			return mapperInterface.toDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/inserir")
	public ResponseEntity<Usuario> inserirUsuario(@RequestBody @Valid Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		repU.save(usuario);
		cacheU.limparCache();
				
		return ResponseEntity.ok(usuario);
	}

	@PutMapping(value = "/atualizar/{id}")
	public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
		
		Optional<Usuario> op = cacheU.findById(id);
		
		if(op.isPresent()) {
			Usuario usuario_antigo = op.get();
			usuario_antigo.setCargo(usuario.getCargo());
			usuario_antigo.setNome(usuario.getNome());
			usuario_antigo.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuario_antigo.setUsername(usuario.getUsername());
			
			repU.save(usuario_antigo);
			cacheU.limparCache();
			
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return usuario;
	}

	@DeleteMapping(value = "/excluir/{id}")
	public Usuario excluirUsuario(@PathVariable Long id) {
		
		Optional<Usuario> op = repU.findById(id);
		
		if (op.isPresent()) {
			Usuario usuario_remover = op.get();
			repU.delete(usuario_remover);
			cacheU.limparCache();	
			return usuario_remover;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
