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
import com.challenge.mottu.model.Galpao;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.GalpaoRepository;
import com.challenge.mottu.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
public class GalpaoControllerMVC {
	
	@Autowired
	private GalpaoRepository repG;
	
	@Autowired
	private UsuarioRepository repU;
	
	@GetMapping("/galpao/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/galpao/index");

		List<Galpao> galpoes = repG.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = repU.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		mv.addObject("galpoes", galpoes);

		return mv;
	}
	
	@GetMapping("/galpao/novo")
	public ModelAndView retornarCadGalpao() {

		ModelAndView mv = new ModelAndView("/galpao/novo");

		mv.addObject("galpao", new Galpao());
		mv.addObject("lista_galpoes", repG.findAll());

		return mv;
	}
	
	@PostMapping("/insere_galpao")
	public ModelAndView cadastrarGalpao(@Valid Galpao galpao) {
		
		Galpao galpao_novo = new Galpao();
		galpao_novo.setNome_galpao(galpao.getNome_galpao());
		
		repG.save(galpao_novo);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/galpao/detalhes/{id}")
	public ModelAndView exibirDetalhesGalpao(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Galpao> op = repG.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/galpao/detalhes");
			mv.addObject("galpao", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/galpao/editar/{id}")
	public ModelAndView exibirPaginaEdicao(@PathVariable Long id){
		
		Optional<Galpao> op = repG.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/galpao/edicao");
			mv.addObject("galpao", op.get());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/galpao/remover/{id}")
	public ModelAndView removerGalpao(@PathVariable Long id) {
		
		Optional<Galpao> op = repG.findById(id);
		
		if(op.isPresent()) {
			
			repG.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
	
}
