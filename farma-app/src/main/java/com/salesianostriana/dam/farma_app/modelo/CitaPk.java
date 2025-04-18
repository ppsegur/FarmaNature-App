package com.salesianostriana.dam.farma_app.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CitaPk implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Column(name = "cliente_id")
    private UUID id_cliente;


    @Column(name = "farpackage com.salesianostriana.dam.farma_app.modelo;\n" +
            "\n" +
            "\n" +
            "import jakarta.persistence.Column;\n" +
            "import jakarta.persistence.Embeddable;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Builder;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import lombok.experimental.SuperBuilder;\n" +
            "import org.springframework.format.annotation.DateTimeFormat;\n" +
            "\n" +
            "import java.io.Serializable;\n" +
            "import java.time.LocalDateTime;\n" +
            "import java.util.UUID;\n" +
            "\n" +
            "@Data\n" +
            "@Embeddable\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class CitaPk implements Serializable {\n" +
            "    /**\n" +
            "     *\n" +
            "     */\n" +
            "    private static final long serialVersionUID = 1L;\n" +
            "\n" +
            "\n" +
            "    @Column(name = \"cliente_id\")\n" +
            "    private UUID id_cliente;\n" +
            "\n" +
            "\n" +
            "    @Column(name = \"farmaceutico_id\")\n" +
            "    private UUID id_farmaceutico;\n" +
            "    @DateTimeFormat(pattern = \"yyyy-MM-dd'T'HH:mm\")\n" +
            "    @Column(name = \"fechaInicio\")\n" +
            "    private LocalDateTime fechaInicio;\n" +
            "\n" +
            "}\nmaceutico_id")
    private UUID id_farmaceutico;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fechaInicio")
    private LocalDateTime fechaInicio;

}
