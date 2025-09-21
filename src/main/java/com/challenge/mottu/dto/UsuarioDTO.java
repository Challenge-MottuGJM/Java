package com.challenge.mottu.dto;

import java.util.HashSet;
import java.util.Set;

import com.challenge.mottu.model.Cargo;

public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String username;
	private String senha;
	private Set<Cargo> cargo = new HashSet<Cargo>();

	public UsuarioDTO() {}

	public UsuarioDTO(Long id, String nome, String username, String senha, Set<Cargo> cargo) {
		super();
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.senha = senha;
		this.cargo = cargo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Cargo> getCargo() {
		return cargo;
	}

	public void setCargo(Set<Cargo> cargo) {
		this.cargo = cargo;
	}
}
