package com.challenge.mottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.repository.AndarRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AndarControllerMVC {
	
	@Autowired
	private AndarRepository repA;
	
	@GetMapping("/andar/novo")
	public ModelAndView retornarCadAndar() {

		ModelAndView mv = new ModelAndView("/andar/novo");

		mv.addObject("andar", new Andar());
		mv.addObject("lista_andares", repA.findAll());

		return mv;
	}
	
	@PostMapping("/insere_andar")
	public ModelAndView cadastrarAndar(@Valid Andar andar) {
		
		Andar andar_novo = new Andar();
		andar_novo.setGalpao(andar.getGalpao());
		andar_novo.setNumero_andar(andar.getNumero_andar());
		
		repA.save(andar_novo);
		
		return new ModelAndView("redirect:/index");
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
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
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
