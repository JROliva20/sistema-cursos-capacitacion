package gt.intecap.apicapacitacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "inscripciones")
public class InscripcionModel extends EntidadBaseModel {

    private LocalDate fechaInscripcion;

    @Size(max = 30)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "participante_id")
    private ParticipanteModel participante;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoModel curso;
}