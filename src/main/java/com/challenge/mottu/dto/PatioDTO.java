package com.challenge.mottu.dto;

import com.challenge.mottu.model.Andar;
import com.challenge.mottu.model.Patio;

public class PatioDTO{

	private Long id;
	private Andar andar;
	private Long numero_patio;
	
	public PatioDTO(){}
	
	public PatioDTO(Patio patio) {
		this.id = patio.getId();
		this.andar = patio.getAndar();
		this.numero_patio = patio.getNumero_patio();
	}
	
	public PatioDTO(Long id, Andar andar, Long numero_patio) {
		super();
		this.id = id;
		this.andar = andar;
		this.numero_patio = numero_patio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Andar getAndar() {
		return andar;
	}
	public void setAndar(Andar andar) {
		this.andar = andar;
	}
	public Long getNumero_patio() {
		return numero_patio;
	}
	public void setNumero_patio(Long numero_patio) {
		this.numero_patio = numero_patio;
	}
	
	
	
}
