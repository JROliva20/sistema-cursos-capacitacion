package gt.intecap.apicapacitacion.repository;

import gt.intecap.apicapacitacion.models.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends
        JpaRepository<CursoModel, Long> {
}