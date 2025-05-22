package com.challenge.mottu.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Esta classe irá representar a entidade Vaga")
@Data
@Entity
@Table(name = "VAGA")
public class Vaga{
	
	@Schema(description = "Este atributo representa a chave primária ID", example = "1")
	@Id
	private Long id;
	@NotNull(message = "Não é permitido incluir vaga sem bloco")
	@ManyToOne
	@JoinColumn(name= "BLOCO_ID")
	private Bloco bloco;
	@NotNull(message = "Não é permitido a inserção de vaga sem número")
	private Long numero_vaga;
	

}
