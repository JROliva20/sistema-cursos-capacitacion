package gt.intecap.apicapacitacion.services;

import gt.intecap.apicapacitacion.enums.EstadoInscripcion;
import gt.intecap.apicapacitacion.models.CursoModel;
import gt.intecap.apicapacitacion.models.InscripcionModel;
import gt.intecap.apicapacitacion.models.ParticipanteModel;
import gt.intecap.apicapacitacion.repository.CursoRepository;
import gt.intecap.apicapacitacion.repository.InscripcionRepository;
import gt.intecap.apicapacitacion.repository.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CursoRepository cursoRepository;
    private final ParticipanteRepository participanteRepository;

    public List<InscripcionModel> listarInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Optional<InscripcionModel> obtenerPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public InscripcionModel registrarInscripcion(Long cursoId, Long participanteId) {
        CursoModel curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        ParticipanteModel participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        InscripcionModel inscripcion = new InscripcionModel();
        inscripcion.setCurso(curso);
        inscripcion.setParticipante(participante);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado(EstadoInscripcion.ACTIVA); // 👈 ¡ESTO FALTABA! Asigna ACTIVA por defecto

        return inscripcionRepository.save(inscripcion);
    }

    public InscripcionModel actualizarInscripcion(Long id, Long cursoId, Long participanteId, LocalDate fechaInscripcion) {
        InscripcionModel inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada con ID: " + id));

        CursoModel curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        ParticipanteModel participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        inscripcion.setCurso(curso);
        inscripcion.setParticipante(participante);
        if (fechaInscripcion != null) {
            inscripcion.setFechaInscripcion(fechaInscripcion);
        }

        // Asegurar que mantenga o tenga el estado activo si no se ha cancelado
        if (inscripcion.getEstado() == null) {
            inscripcion.setEstado(EstadoInscripcion.ACTIVA);
        }

        return inscripcionRepository.save(inscripcion);
    }

    public void cancelarInscripcion(Long id) {
        InscripcionModel inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        inscripcionRepository.delete(inscripcion);
    }
}