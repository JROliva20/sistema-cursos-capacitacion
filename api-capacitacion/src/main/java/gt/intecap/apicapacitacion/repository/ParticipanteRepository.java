package gt.intecap.apicapacitacion.repository;

import gt.intecap.apicapacitacion.models.ParticipanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends
        JpaRepository<ParticipanteModel, Long> {
}