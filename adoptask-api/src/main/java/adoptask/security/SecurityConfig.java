package adoptask.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/api/usuarios/*/favoritos", "/api/protectoras/*/voluntarios", "/api/protectoras/*/animales", 
            			"/api/protectoras/*/tareas", "/api/protectoras/*/documentos", "/api/protectoras/*/historial").authenticated()
                .requestMatchers(POST, "/api/auth/login", "/api/usuarios", "/api/publicaciones/busqueda").permitAll()
                .requestMatchers(GET, "/api/publicaciones/*", "/api/usuarios/*/*", "/api/protectoras", "/api/protectoras/*/*", 
                		"/api/protectoras/*/animales/*/imagenes/*", "/api/protectoras/*/animales/*/documentos/*",
                		"/api/protectoras/*/documentos/*").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(login -> login.disable());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
    		throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
