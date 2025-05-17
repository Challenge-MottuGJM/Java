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

@Schema(description = "Esta classe irá representar a entidade Andar")
@Data
@Entity
@Table(name = "ANDAR")
public class Andar extends RepresentationModel<Andar>{
	
	@Schema(description = "Este atributo representa a chave primária ID", example = "1")
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name= "GALPAO_ID")
	private Galpao galpao;
	@NotEmpty(message = "Não é permitido a inserção de andar sem número de identificação")
	private Long numero_andar;

}
