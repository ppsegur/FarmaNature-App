package com.salesianostriana.dam.farma_app.modelo;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED) //Para gestionar la herencia la haremos tipo joined mucho más facil
@Table(name = "usuario_entity")
public abstract class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String username ;

    private String passsword;
    private String email;

    private boolean verificado;
    private String nombre;
    private String apellidos;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return roles.stream()
                .map(role -> "ROLE_"+role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
