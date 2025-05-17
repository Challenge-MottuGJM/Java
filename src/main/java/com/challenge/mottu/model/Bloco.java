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

@Schema(description = "Esta classe irá representar a entidade Bloco")
@Data
@Entity
@Table(name = "BLOCO")
public class Bloco extends RepresentationModel<Bloco>{
	
	@Schema(description = "Este atributo representa a chave primária ID", example = "1")
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name= "PATIO_ID")
	private Patio patio;
	@NotEmpty(message = "Não é permitido a inserção de bloco sem letra de identificação")
	private String letra_bloco;

}
