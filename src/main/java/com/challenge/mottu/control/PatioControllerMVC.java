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

import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.model.Patio;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.AndarRepository;
import com.challenge.mottu.repository.PatioRepository;
import com.challenge.mottu.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class PatioControllerMVC {

	@Autowired
	private PatioRepository repP;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private AndarRepository repA;
	
	@GetMapping("/patio/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/patio/index");

		List<Patio> patios = repP.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = repU.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		mv.addObject("patios", patios);
		mv.addObject("lista_andares", repA.findAll());

		return mv;
	}
	
	@GetMapping("/patio/novo")
	public ModelAndView retornarCadPatio() {

		ModelAndView mv = new ModelAndView("/patio/novo");

		mv.addObject("patio", new Patio());
		mv.addObject("lista_andares", repA.findAll());

		return mv;
	}
	
	@PostMapping("/patio/insere_patio")
	public ModelAndView cadastrarPatio(@Valid Patio patio, BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/patio/novo");
			mv.addObject("patio", patio);
			mv.addObject("lista_andares", repA.findAll());
			return mv;
			
		} else {
		
			Patio patio_novo = new Patio();
			patio_novo.setAndar(patio.getAndar());
			patio_novo.setNumero_patio(patio.getNumero_patio());
			
			repP.save(patio_novo);
			
			return new ModelAndView("redirect:/index");
		}
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
			mv.addObject("lista_andares", repA.findAll());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@PostMapping("/patio/atualizar_patio/{id}")
	public ModelAndView atualizarPatio(@PathVariable Long id, @Valid Patio patio, BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/patio/edicao");
			mv.addObject("patio", patio);
			mv.addObject("lista_andares", repA.findAll());
			return mv;
			
		} else {
			Optional<Patio> op = repP.findById(id);
			
			if(op.isPresent()) {
				
				Patio patio_antigo = op.get();
				patio_antigo.setNumero_patio(patio.getNumero_patio());
				patio_antigo.setAndar(patio.getAndar());
				repP.save(patio_antigo);
				return new ModelAndView("redirect:/index");
				
			} else {
				return new ModelAndView("redirect:/index");
			}
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
