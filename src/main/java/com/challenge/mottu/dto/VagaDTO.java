package com.challenge.mottu.dto;

import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.model.Vaga;


public class VagaDTO{
	
	private Long id;
	private Bloco bloco;
	private Long numero_vaga;
	
	public VagaDTO() {}
	
	public VagaDTO(Vaga vaga) {
		this.id = vaga.getId();
		this.bloco = vaga.getBloco();
		this.numero_vaga = vaga.getNumero_vaga();	
	}
	
	public VagaDTO(Long id, Bloco bloco, Long numero_vaga) {
		super();
		this.id = id;
		this.bloco = bloco;
		this.numero_vaga = numero_vaga;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Bloco getBloco() {
		return bloco;
	}
	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}
	public Long getNumero_vaga() {
		return numero_vaga;
	}
	public void setNumero_vaga(Long numero_vaga) {
		this.numero_vaga = numero_vaga;
	}
	
	

}
