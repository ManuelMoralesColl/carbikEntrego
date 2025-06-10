package com.carbik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .cors(cors -> {}) // Habilita CORS
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // Rutas públicas
        	    .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
        	    .requestMatchers("/api/auth/**").permitAll()
        	    .requestMatchers(HttpMethod.GET, "/api/archivos/**").permitAll()
        	    .requestMatchers(HttpMethod.POST, "/api/archivos/**").permitAll()
        	    .requestMatchers(HttpMethod.GET, "/api/feed/**").permitAll()
        	    .requestMatchers(HttpMethod.GET, "/api/seguimientos/**").permitAll()
        	    .requestMatchers(HttpMethod.POST, "/api/seguimientos/**").permitAll()
        	    .requestMatchers(HttpMethod.GET, "/api/secciones/**").permitAll()
        	    
        	    .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").authenticated()
        	    .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").authenticated()
        	    .requestMatchers(HttpMethod.GET, "/api/usuarios/**").authenticated()
        	    .requestMatchers(HttpMethod.POST, "/api/publicaciones/**").authenticated()
        	    .requestMatchers(HttpMethod.GET, "/api/publicaciones/**").authenticated()
        	    .requestMatchers(HttpMethod.POST, "/api/vehiculos/**").authenticated()
        	    .requestMatchers(HttpMethod.GET, "/api/vehiculos/**").authenticated()
        	    
        	    //.requestMatchers(HttpMethod.GET, "/api/seguimientos/**").authenticated()

        	    

            
            // Todo lo demás requiere autenticación
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}