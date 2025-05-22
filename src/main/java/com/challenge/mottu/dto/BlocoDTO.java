package com.challenge.mottu.dto;

import com.challenge.mottu.model.Bloco;
import com.challenge.mottu.model.Patio;

public class BlocoDTO{

	private Long id;
	private String letra_bloco;
	private Patio patio;
	
	public BlocoDTO() {}
	
	public BlocoDTO(Bloco bloco) {
		this.id = bloco.getId();
		this.patio = bloco.getPatio();
		this.letra_bloco = bloco.getLetra_bloco();
	}
	public BlocoDTO(Long id, String letra_bloco, Patio patio) {
		super();
		this.id = id;
		this.letra_bloco = letra_bloco;
		this.patio = patio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLetra_bloco() {
		return letra_bloco;
	}
	public void setLetra_bloco(String letra_bloco) {
		this.letra_bloco = letra_bloco;
	}
	public Patio getPatio() {
		return patio;
	}
	public void setPatio(Patio patio) {
		this.patio = patio;
	}
	
	
}
