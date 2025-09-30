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

import com.challenge.mottu.model.Moto;
import com.challenge.mottu.model.Usuario;
import com.challenge.mottu.repository.MotoRepository;
import com.challenge.mottu.repository.UsuarioRepository;
import com.challenge.mottu.repository.VagaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class MotoControllerMVC {
	
	@Autowired
	private MotoRepository repM;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private VagaRepository repV;
	
	@GetMapping("/moto/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/moto/index");

		List<Moto> motos = repM.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = repU.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		mv.addObject("motos", motos);
		mv.addObject("lista_vagas", repV.findAll());

		return mv;
	}
	
	@GetMapping("/moto/novo")
	public ModelAndView retornarCadMoto() {

		ModelAndView mv = new ModelAndView("/moto/novo");

		mv.addObject("moto", new Moto());
		mv.addObject("lista_vagas", repV.findAll());

		return mv;
	}
	
	@PostMapping("/insere_moto")
	public ModelAndView cadastrarMoto(@Valid Moto moto) {
		
		Moto moto_nova = new Moto();
		moto_nova.setChassi(moto.getChassi());
		moto_nova.setMarca(moto.getMarca());
		moto_nova.setModelo(moto.getModelo());
		moto_nova.setPlaca(moto.getPlaca());
		moto_nova.setStatus(moto.getStatus());
		moto_nova.setVaga(moto.getVaga());
		
		repM.save(moto_nova);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/moto/detalhes/{id}")
	public ModelAndView exibirDetalhesMoto(HttpServletRequest request, @PathVariable Long id) {
		
		Optional<Moto> op = repM.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/moto/detalhes");
			mv.addObject("moto", op.get());
			mv.addObject("uri", request.getRequestURI());
			return mv;
			  
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/moto/editar/{id}")
	public ModelAndView exibirPaginaMoto(@PathVariable Long id){
		
		Optional<Moto> op = repM.findById(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/moto/edicao");
			mv.addObject("moto", op.get());
			mv.addObject("lista_vagas", repV.findAll());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	
	@GetMapping("/moto/remover/{id}")
	public ModelAndView removerMoto(@PathVariable Long id) {
		
		Optional<Moto> op = repM.findById(id);
		
		if(op.isPresent()) {
			
			repM.deleteById(id);
			
			return new ModelAndView("redirect:/index");
			
		} else {
			return new ModelAndView("redirect:/index");
		}	
	}

}
