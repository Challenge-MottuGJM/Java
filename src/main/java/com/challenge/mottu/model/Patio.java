package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "PATIO")
public class Patio{

	@Id
	private Long id;
	@NotNull(message = "Não é permitido incluir patio sem andar")
	@ManyToOne
	@JoinColumn(name= "ANDAR_ID")
	private Andar andar;
	@NotNull(message = "Não é permitido a inserção de patio sem número de identificação")
	private Long numero_patio;
}
