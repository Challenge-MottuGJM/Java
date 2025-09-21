package com.challenge.mottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.mottu.model.Vaga;
import com.challenge.mottu.repository.VagaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class VagaControllerMVC {

	@Autowired
	private VagaRepository repV;
	
	@GetMapping("/vaga/novo")
	public ModelAndView retornarCadVaga() {

		ModelAndView mv = new ModelAndView("/vaga/novo");

		mv.addObject("vaga", new Vaga());
		mv.addObject("lista_vagas", repV.findAll());

		return mv;
	}
	
	@PostMapping("/insere_vaga")
	public ModelAndView cadastrarVaga(@Valid Vaga vaga) {
		
		Vaga vaga_novo = new Vaga();
		vaga_novo.setBloco(vaga.getBloco());
		vaga_novo.setNumero_vaga(vaga.getNumero_vaga());
		
		repV.save(vaga_novo);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/vaga/detalhes/{id}")
	public ModelAndView exibirDetalhesVaga(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Vaga> op = repV.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/vaga/detalhes");
			mv.addObject("vaga", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/vaga/editar/{id}")
	public ModelAndView exibirPaginaVaga(@PathVariable Long id){
		
		Optional<Vaga> op = repV.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/vaga/edicao");
			mv.addObject("vaga", op.get());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/vaga/remover/{id}")
	public ModelAndView removerVaga(@PathVariable Long id) {
		
		Optional<Vaga> op = repV.findById(id);
		
		if(op.isPresent()) {
			
			repV.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
}
