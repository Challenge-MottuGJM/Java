package com.challenge.mottu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "GALPAO")
public class Galpao{

	@Id
	private Long id;
	@NotEmpty(message = "Não é permitido a inserção de galpao sem nome de identificação")
	private String nome_galpao;
}
