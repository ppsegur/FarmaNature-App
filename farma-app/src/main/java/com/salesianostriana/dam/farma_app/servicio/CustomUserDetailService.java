package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.repositorio.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
