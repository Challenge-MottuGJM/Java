package com.challenge.mottu.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String username;
	private String senha;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_CARGO", 
	joinColumns = @JoinColumn(name = "USUARIO_ID"), 
	inverseJoinColumns = @JoinColumn(name = "CARGO_ID") )
	private Set<Cargo> cargo = new HashSet<Cargo>();

	public Usuario() {}

	public Usuario(Long id, String nome, String username, String senha, Set<Cargo> cargo) {
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
