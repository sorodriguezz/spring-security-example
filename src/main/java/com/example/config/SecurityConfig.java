package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Para que spring lo tome como una clase de configuracion
@EnableWebSecurity // Habilitar clase de configuracion de spring security
public class SecurityConfig {

    // El HttpSecurity es una clase bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // El CSRF se recomienda dejar activado, ya que sirve para una vulnerabilidad de los formularios y solicitudes
                // ya que el atacante puede interceptar la comunicacion con el navegador (Cross-Site Request Forgery)
                // esto esta activando por defect, si lo dejamos así lo desactivamos
                // .csrf().disable()
                .authorizeHttpRequests((authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers("/v1/index2").permitAll() // rutas que tendran libre acceso
                            .anyRequest().authenticated(); // todas las demas rutas requieren authorizacion
                })) // permite que todos puedan entrar al formulario
                .build();

        return http.build();
    }
}

/**
 * ejemplo springboot 3.1.0
 *
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 * http.securityMatcher("/api/**").authorizeHttpRequests(rmr -> rmr
 * .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
 * .requestMatchers("/api/**").authenticated()
 * ).httpBasic(httpbc -> httpbc
 * .authenticationEntryPoint(authenticationEntryPoint)
 * ).sessionManagement(smc -> smc
 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
 * ).csrf(AbstractHttpConfigurer::disable);
 * return http.build();
 * }
 */