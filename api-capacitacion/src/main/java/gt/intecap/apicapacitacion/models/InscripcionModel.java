package gt.intecap.apicapacitacion.models;

import gt.intecap.apicapacitacion.enums.EstadoInscripcion;
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

    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;

    @ManyToOne
    @JoinColumn(name = "participante_id")
    private ParticipanteModel participante;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoModel curso;
}