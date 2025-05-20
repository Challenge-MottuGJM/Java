package com.challenge.mottu.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	OpenAPI configurarSwagger() {
		return new OpenAPI().info(new Info()
											.title("Projeto de Gestão de Galpão - FIAP x Mottu")
											.description("Este projeto visa gerenciar as relações e "
											+ "as principais funcionalidades referentes aos relacionamentos"
											+ " entre os elementos "
											+ "galpão, andar, patio, bloco, vaga e motos "
											+ "em uma aplicação de alto nível de "
											+ "maturidade utilizando de recursos do framework SpringBoot")
											.summary("Este projeto visa gerenciar um sistema de galpão de motos alugadas")
											.version("v1.0.0")
											.license(new License().url("https://mottu.com.br/")
																  .name("Projeto de Gestão de Galpão - FIAP x Mottu")));
	}

}
