package gt.intecap.clientecapacitacion.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDto {
    private Long id;
    private Long cursoId;
    private Long participanteId;
    private LocalDate fechaInscripcion;
    private String estado;

    private CursoDto curso;
    private ParticipanteDto participante;
}