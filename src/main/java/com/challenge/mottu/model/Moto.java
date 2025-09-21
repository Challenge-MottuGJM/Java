package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "MOTO")
public class Moto{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "VAGA_ID")
	private Vaga vaga;
	
	private String status;
	@NotEmpty(message = "Não é permitido a inserção de motos sem Modelo")
	private String modelo;
	@NotEmpty(message = "Não é permitido a inserção de motos sem Marca")
	private String marca;
	private String placa;
	@NotEmpty(message = "Não é permitido a inserção de motos sem Chassi")
	private String chassi; 
	

}
