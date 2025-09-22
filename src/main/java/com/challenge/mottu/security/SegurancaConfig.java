package com.challenge.mottu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurancaConfig {
	
	@Bean
	public SecurityFilterChain filtrar(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((request) -> 
		request.requestMatchers("/usuario/novo",
								"/usuario/editar/{id}", 
								"/usuario/remover/{id}",
								"/andar/novo",
								"/andar/editar/{id}",
								"/andar/remover/{id}",
								"/bloco/novo",
								"/bloco/editar/{id}",
								"/bloco/remover/{id}",
								"/galpao/novo",
								"/galpao/editar/{id}",
								"/galpao/remover/{id}",
								"/patio/novo",
								"/patio/editar/{id}",
								"/patio/remover/{id}",
								"/vaga/novo",
								"/vaga/editar/{id}",
								"/vaga/remover/{id}",
								"/moto/novo",
								"/moto/editar/{id}",
								"/moto/remover/{id}"
								).hasAuthority("ADMINISTRADOR").				
				anyRequest().authenticated())
			.formLogin( (login) -> login.loginPage("/login").defaultSuccessUrl("/index", true)
					.failureUrl("/login?falha=true").permitAll())
			.logout( (logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")
					.permitAll())
			.exceptionHandling((exception) -> 
			exception.accessDeniedHandler((request,response,AccessDeniedException) -> 
			{response.sendRedirect("/acesso_negado");}) );
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
