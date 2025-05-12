package com.challenge.mottu.dto;

import org.springframework.hateoas.RepresentationModel;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.model.Galpao;

public class AndarDTO extends RepresentationModel<AndarDTO> {
	
	private Long id;
	private Long numero_andar;
	private Galpao galpao;
	
	public AndarDTO() {}
	
	public AndarDTO(Long id, Long numero_andar, Galpao galpao) {
		super();
		this.id = id;
		this.numero_andar = numero_andar;
		this.galpao = galpao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumero_andar() {
		return numero_andar;
	}
	public void setNumero_andar(Long numero_andar) {
		this.numero_andar = numero_andar;
	}
	public Galpao getGalpao() {
		return galpao;
	}
	public void setGalpao(Galpao galpao) {
		this.galpao = galpao;
	}
	
	

}
