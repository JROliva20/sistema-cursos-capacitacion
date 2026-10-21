package gt.intecap.apicapacitacion.models;

import gt.intecap.apicapacitacion.enums.Modalidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "cursos")
public class CursoModel extends EntidadBaseModel {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20)
    private String codigo;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 100)
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    @NotNull(message = "El cupo es obligatorio")
    private Integer cupo;

    private LocalDate fechaInicio;

    private Boolean activo;
}