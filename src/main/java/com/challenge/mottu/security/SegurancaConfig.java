package com.challenge.mottu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SegurancaConfig {
	
	@Autowired
    private JWTAuthFilter jwtAuthFilter;
	
	@Bean
    public SecurityFilterChain filtrar(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**").disable()) 
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

            .authorizeHttpRequests(request -> request
                .requestMatchers("/auth/login", "/api/**").permitAll()

                // regras MVC com base nas roles conforme precisa
                .requestMatchers("/usuario/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/vaga/**", "/moto/**").hasAnyAuthority("ADMINISTRADOR", "SUPERVISOR", "OPERADOR")
                .requestMatchers("/andar/**", "/bloco/**", "/galpao/**", "/patio/**")
                    .hasAnyAuthority("ADMINISTRADOR", "SUPERVISOR")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?falha=true")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll())
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, ex) -> response.sendRedirect("/acesso_negado")))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
