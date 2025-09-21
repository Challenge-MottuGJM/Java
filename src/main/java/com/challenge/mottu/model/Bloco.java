package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "BLOCO")
public class Bloco{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Não é permitido incluir bloco sem patio")
	@ManyToOne
	@JoinColumn(name= "PATIO_ID")
	@NotNull(message = "Não é permitido incluir bloco sem patio")
	private Patio patio;
	@NotEmpty(message = "Não é permitido a inserção de bloco sem letra de identificação")
	private String letra_bloco;

}
