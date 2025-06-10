package com.salesianostriana.dam.farma_app.seguridad;

import com.salesianostriana.dam.farma_app.seguridad.access.JwtAuthentificationFilter;
import com.salesianostriana.dam.farma_app.seguridad.exceptionHandling.JwtAccessDeniedHandler;
import com.salesianostriana.dam.farma_app.seguridad.exceptionHandling.JwtAuthenticationEntryPoint;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthentificationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;



    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder.authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder);
        return p;

    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(excepz -> excepz
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login","/auth/verify-2fa", "/auth/refresh/token", "/error", "/activate/account/").permitAll()
                .requestMatchers("/me/admin").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/auth/todos").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.PUT, "/auth/**").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.DELETE,"/auth/{username}").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.PUT, "/cliente/**").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/farmaceutico/**").hasRole("FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/categoria/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/categoria").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.PUT,"/categoria/**").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.DELETE,"/categoria/**").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/categoria/top-categoria").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/producto/all").permitAll()
                .requestMatchers(HttpMethod.GET,"/categoria/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/producto/**").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET ,"/producto/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/producto/{id}").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.PUT,"/producto/**").hasAnyRole("ADMIN","FARMACETICO")
                .requestMatchers(HttpMethod.GET,"/buscar/**").permitAll()
                .requestMatchers(HttpMethod.POST ,"/upload/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/productoCategoria/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/comentario/").hasRole("CLIENTE")
               // .requestMatchers(HttpMethod.GET,"/comentario/cliente/{username}").permitAll()
               .requestMatchers(HttpMethod.GET,"/comentario/cliente-que-mas-comenta").permitAll()
               .requestMatchers(HttpMethod.GET, "/comentario/producto-con-mas-comentarios").permitAll()
                .requestMatchers(HttpMethod.GET,"/comentario/cliente/{username}").permitAll()
                .requestMatchers(HttpMethod.GET,"/comentario/producto/{username}").permitAll()
                .requestMatchers(HttpMethod.GET,"/comentario/all").permitAll()
                .requestMatchers(HttpMethod.GET,"/comentario/top3-productos-mas-comentados").permitAll()
                .requestMatchers(HttpMethod.GET, "/comentario/producto-con-mas-comentarios").permitAll()
               .requestMatchers(HttpMethod.GET,"/comentario/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/comentario/editar/**").hasAnyRole("CLIENTE")
                .requestMatchers("/carrito").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**","/swagger-ui.html").permitAll()

                .requestMatchers("/carrito/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/comentario/eliminar/**").hasAnyRole("CLIENTE", "ADMIN", "FARMACEUTICO")
                .requestMatchers(HttpMethod.POST,"/citas/").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET,"/citas/all").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.DELETE,"/citas/**").hasAnyRole("FARMACEUTICO","ADMIN")
                .requestMatchers(HttpMethod.GET,"/citas/cliente/**").hasRole("FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/citas/farmaceutico/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/citas/**").hasAnyRole("FARMACEUTICO","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/citas/**").hasAnyRole("FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/citas/top-farmaceutico").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/citas/top-cliente").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET, "/usuarios/username/{username}").permitAll()
                .requestMatchers(HttpMethod.GET,"/producto-estrella-categoria").permitAll()
                .requestMatchers(HttpMethod.GET,"/producto-estrella-usuario").permitAll()
                .requestMatchers(HttpMethod.GET,"/citas/numero-citas-cliente/{username}").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET,"/citas/numero-citas-farmaceutico/{username}").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET, "/citas/media/dia").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.GET, "/citas/media/mes").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers(HttpMethod.POST,"/citas/crear").hasAnyRole("ADMIN","FARMACEUTICO")
        .requestMatchers(HttpMethod.PATCH, "/citas/{ClienteId}/{farmaciaId}/{fechaInicio}/anular").hasAnyRole("ADMIN","FARMACEUTICO")
                .requestMatchers("/h2-console","/auth/qr-code/**","/auth/verify-2fa").permitAll()
                .anyRequest().authenticated());


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }

}
