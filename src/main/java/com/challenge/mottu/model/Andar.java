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
@Table(name = "ANDAR")
public class Andar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Não é permitido incluir andar sem galpao")
	@ManyToOne
	@JoinColumn(name= "GALPAO_ID")
	private Galpao galpao;
	@NotNull(message = "Não é permitido a inserção de andar sem número de identificação")
	private Long numero_andar;

}
