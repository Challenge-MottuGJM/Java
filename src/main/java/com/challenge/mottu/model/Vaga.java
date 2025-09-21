package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "VAGA")
public class Vaga{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Não é permitido incluir vaga sem bloco")
	@ManyToOne
	@JoinColumn(name= "BLOCO_ID")
	private Bloco bloco;
	@NotNull(message = "Não é permitido a inserção de vaga sem número")
	private Long numero_vaga;
	

}
