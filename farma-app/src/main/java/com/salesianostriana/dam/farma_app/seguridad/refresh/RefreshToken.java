package com.salesianostriana.dam.farma_app.seguridad.refresh;


import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {

        @Id
        @GeneratedValue
        private UUID id;

        //@MapsId
        @OneToOne
        @JoinColumn(name = "usuario_id")
        private Usuario user;


        @Column(nullable = false)
        private Instant expireAt;

        @Builder.Default
        private Instant createdAt = Instant.now();

        public String getToken() {
            return id.toString();
        }
}
