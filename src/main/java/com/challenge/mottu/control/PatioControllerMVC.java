package com.challenge.mottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.mottu.model.Patio;
import com.challenge.mottu.repository.PatioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class PatioControllerMVC {

	@Autowired
	private PatioRepository repP;
	
	@GetMapping("/patio/novo")
	public ModelAndView retornarCadPatio() {

		ModelAndView mv = new ModelAndView("/patio/novo");

		mv.addObject("patio", new Patio());
		mv.addObject("lista_patios", repP.findAll());

		return mv;
	}
	
	@PostMapping("/insere_patio")
	public ModelAndView cadastrarPatio(@Valid Patio patio) {
		
		Patio patio_novo = new Patio();
		patio_novo.setAndar(patio.getAndar());
		patio_novo.setNumero_patio(patio.getNumero_patio());
		
		repP.save(patio_novo);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/patio/detalhes/{id}")
	public ModelAndView exibirDetalhesPatio(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Patio> op = repP.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/patio/detalhes");
			mv.addObject("patio", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/patio/editar/{id}")
	public ModelAndView exibirPaginaPatio(@PathVariable Long id){
		
		Optional<Patio> op = repP.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/patio/edicao");
			mv.addObject("patio", op.get());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/patio/remover/{id}")
	public ModelAndView removerPatio(@PathVariable Long id) {
		
		Optional<Patio> op = repP.findById(id);
		
		if(op.isPresent()) {
			
			repP.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
}
