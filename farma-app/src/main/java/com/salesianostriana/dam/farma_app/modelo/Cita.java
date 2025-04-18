package com.salesianostriana.dam.farma_app.modelo;

import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cita")
public class Cita {

    @EmbeddedId
    private CitaPk citasPk = new CitaPk();

    @ManyToOne
    @MapsId("id_farmaceutico")
    @JoinColumn(name = "id_farmaceutico")
    private Farmaceutico farmaceutico;

    @ManyToOne
    @MapsId("id_cliente")
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private String titulo;

    @Column(name = "fechaInicio",insertable = false, updatable = false)
    private LocalDateTime fechaInicio;

    private LocalDateTime fecha_fin;
    private double precioCita;
    private boolean especial;

    // Métodos para gestionar relaciones
    public void addToFarmaceutico(Farmaceutico farmaceutico) {
        farmaceutico.getCitas().add(this);
        this.farmaceutico = farmaceutico;
    }

    public void removeFromFarmaceutico(Farmaceutico farmaceutico) {
        farmaceutico.getCitas().remove(this);
        this.farmaceutico = null;
    }

    public void addToCliente(Cliente cliente) {
        cliente.getCitas().add(this);
        this.cliente = cliente;
    }

    public void removeFromCliente(Cliente cliente) {
        cliente.getCitas().remove(this);
        this.cliente = null;
    }

    // Equals y HashCode
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cita cita = (Cita) o;
        return getCitasPk() != null && Objects.equals(getCitasPk(), cita.getCitasPk());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(citasPk);
    }
}