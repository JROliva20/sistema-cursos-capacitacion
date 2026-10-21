package gt.intecap.apicapacitacion.repository;

import gt.intecap.apicapacitacion.enums.EstadoInscripcion;
import gt.intecap.apicapacitacion.models.InscripcionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {

    boolean existsByCursoIdAndParticipanteId(Long cursoId, Long participanteId);

    long countByCursoIdAndEstado(Long cursoId, EstadoInscripcion estado);
}