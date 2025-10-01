package com.challenge.mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.AndarRepository;
import com.challenge.mottu.repository.GalpaoRepository;
import com.challenge.mottu.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AndarControllerMVC {
	
	@Autowired
	private AndarRepository repA;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private GalpaoRepository repG;
	
	@GetMapping("/andar/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/andar/index");

		List<Andar> andares = repA.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = repU.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		mv.addObject("andares", andares);
		mv.addObject("lista_galpoes", repG.findAll());

		return mv;
	}
	
	@GetMapping("/andar/novo")
	public ModelAndView retornarCadAndar() {

		ModelAndView mv = new ModelAndView("/andar/novo");

		mv.addObject("andar", new Andar());
		mv.addObject("lista_galpoes", repG.findAll());

		return mv;
	}
	
	@PostMapping("/andar/insere_andar")
	public ModelAndView cadastrarAndar(@Valid Andar andar, BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/andar/novo");
			mv.addObject("andar", andar);
			mv.addObject("lista_galpoes", repG.findAll());
			return mv;
			
		} else {
		
			Andar andar_novo = new Andar();
			andar_novo.setGalpao(andar.getGalpao());
			andar_novo.setNumero_andar(andar.getNumero_andar());
			
			repA.save(andar_novo);
			
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/andar/detalhes/{id}")
	public ModelAndView exibirDetalhesAndar(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Andar> op = repA.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/andar/detalhes");
			mv.addObject("andar", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/andar/editar/{id}")
	public ModelAndView exibirPaginaAndar(@PathVariable Long id){
		
		Optional<Andar> op = repA.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/andar/edicao");
			mv.addObject("andar", op.get());
			mv.addObject("lista_galpoes", repG.findAll());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@PostMapping("/andar/atualizar_andar/{id}")
	public ModelAndView atualizarAndar(@PathVariable Long id, @Valid Andar andar, BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/andar/edicao");
			mv.addObject("andar", andar);
			mv.addObject("lista_galpoes", repG.findAll());
			return mv;
			
		} else {
			Optional<Andar> op = repA.findById(id);
			
			if(op.isPresent()) {
				
				Andar andar_antigo = op.get();
				andar_antigo.setNumero_andar(andar.getNumero_andar());
				andar_antigo.setGalpao(andar.getGalpao());
				repA.save(andar_antigo);
				return new ModelAndView("redirect:/index");
				
			} else {
				return new ModelAndView("redirect:/index");
			}
		}
	}
	
	@GetMapping("/andar/remover/{id}")
	public ModelAndView removerAndar(@PathVariable Long id) {
		
		Optional<Andar> op = repA.findById(id);
		
		if(op.isPresent()) {
			
			repA.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
}
