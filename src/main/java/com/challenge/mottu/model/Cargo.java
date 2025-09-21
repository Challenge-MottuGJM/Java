package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CARGO")
public class Cargo {
	
	@Id
	private Long id;
	@Enumerated(EnumType.STRING)
	private EnumCargo nome;
	
	public Cargo() {}

	public Cargo(Long id, EnumCargo nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumCargo getNome() {
		return nome;
	}

	public void setNome(EnumCargo nome) {
		this.nome = nome;
	}
	

}
