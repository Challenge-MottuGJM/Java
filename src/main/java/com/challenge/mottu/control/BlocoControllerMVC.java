package com.challenge.mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.BlocoRepository;
import com.challenge.mottu.repository.PatioRepository;
import com.challenge.mottu.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BlocoControllerMVC {

	@Autowired
	private BlocoRepository repB;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private PatioRepository repP;
	
	@GetMapping("/bloco/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/bloco/index");

		List<Bloco> blocos = repB.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = repU.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		mv.addObject("blocos", blocos);
		mv.addObject("lista_patios", repP.findAll());

		return mv;
	}
	
	@GetMapping("/bloco/novo")
	public ModelAndView retornarCadBloco() {

		ModelAndView mv = new ModelAndView("/bloco/novo");

		mv.addObject("bloco", new Bloco());
		mv.addObject("lista_patios", repP.findAll());

		return mv;
	}
	
	@PostMapping("/insere_bloco")
	public ModelAndView cadastrarBloco(@Valid Bloco bloco) {
		
		Bloco bloco_novo = new Bloco();
		bloco_novo.setPatio(bloco.getPatio());
		bloco_novo.setLetra_bloco(bloco.getLetra_bloco());
		
		repB.save(bloco_novo);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/bloco/detalhes/{id}")
	public ModelAndView exibirDetalhesBloco(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Bloco> op = repB.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/bloco/detalhes");
			mv.addObject("bloco", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/bloco/editar/{id}")
	public ModelAndView exibirPaginaBloco(@PathVariable Long id){
		
		Optional<Bloco> op = repB.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/bloco/edicao");
			mv.addObject("bloco", op.get());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/bloco/remover/{id}")
	public ModelAndView removerBloco(@PathVariable Long id) {
		
		Optional<Bloco> op = repB.findById(id);
		
		if(op.isPresent()) {
			
			repB.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
}
