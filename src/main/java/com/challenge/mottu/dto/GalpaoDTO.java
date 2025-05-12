package com.challenge.mottu.dto;

import org.springframework.hateoas.RepresentationModel;

import com.challenge.mottu.model.Galpao;

public class GalpaoDTO extends RepresentationModel<GalpaoDTO>{
	
	private Long id;
	private String nome_galpao;
	
	public GalpaoDTO() {}
	
	public GalpaoDTO(Galpao galpao) {
		this.id = galpao.getId();
		this.nome_galpao = galpao.getNome_galpao();
	}
	
	public GalpaoDTO(Long id, String nome_galpao) {
		super();
		this.id = id;
		this.nome_galpao = nome_galpao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome_galpao() {
		return nome_galpao;
	}
	public void setNome_galpao(String nome_galpao) {
		this.nome_galpao = nome_galpao;
	}
	
	

}
