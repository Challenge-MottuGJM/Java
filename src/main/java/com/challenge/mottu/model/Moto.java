package com.challenge.mottu.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "Esta classe irá representar a entidade Moto")
@Data
@Entity
@Table(name = "MOTO")
public class Moto extends RepresentationModel<Moto>{
	
	@Schema(description = "Este atributo representa a chave primária ID", example = "1")
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name = "VAGA_ID")
	private Vaga vaga;
	
	private String status;
	@NotEmpty(message = "Não é mermitido a inserção de motos sem Modelo")
	private String modelo;
	@NotEmpty(message = "Não é mermitido a inserção de motos sem Marca")
	private String marca;
	@NotEmpty(message = "Não é mermitido a inserção de motos sem Placa")
	private String placa;
	@NotEmpty(message = "Não é mermitido a inserção de motos sem Chassi")
	private String chassi; 
	

}
