package gt.intecap.apicapacitacion.services;
import gt.intecap.apicapacitacion.enums.EstadoInscripcion;
import gt.intecap.apicapacitacion.models.CursoModel;
import gt.intecap.apicapacitacion.repository.CursoRepository;
import gt.intecap.apicapacitacion.repository.InscripcionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<CursoConDetalleDto> listarCursosConDetalle() {
        List<CursoModel> cursos = cursoRepository.findAll();
        return cursos.stream().map(curso -> {
            long inscritos = inscripcionRepository.countByCursoIdAndEstado(curso.getId(), EstadoInscripcion.ACTIVA);
            int cupoMaximo = curso.getCupo() != null ? curso.getCupo() : 0;
            int disponible = cupoMaximo - (int) inscritos;

            return new CursoConDetalleDto(
                    curso.getId(),
                    curso.getCodigo(),
                    curso.getNombre(),
                    curso.getDescripcion(),
                    curso.getModalidad() != null ? curso.getModalidad().name() : null,
                    cupoMaximo,
                    curso.getFechaInicio(),
                    curso.getActivo(),
                    (int) inscritos,
                    Math.max(disponible, 0)
            );
        }).collect(Collectors.toList());
    }

    public Optional<CursoModel> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public CursoModel guardarCurso(CursoModel curso) {
        if (curso.getCupo() == null || curso.getCupo() < 1 || curso.getCupo() > 40) {
            throw new IllegalArgumentException("El cupo del curso debe estar entre 1 y 40.");
        }
        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    @Data
    public static class CursoConDetalleDto {
        private Long id;
        private String codigo;
        private String nombre;
        private String descripcion;
        private String modalidad;
        private Integer cupo;
        private java.time.LocalDate fechaInicio;
        private Boolean activo;
        private Integer totalInscritos;
        private Integer cupoDisponible;

        public CursoConDetalleDto(Long id, String codigo, String nombre, String descripcion, String modalidad, Integer cupo, java.time.LocalDate fechaInicio, Boolean activo, Integer totalInscritos, Integer cupoDisponible) {
            this.id = id;
            this.codigo = codigo;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.modalidad = modalidad;
            this.cupo = cupo;
            this.fechaInicio = fechaInicio;
            this.activo = activo;
            this.totalInscritos = totalInscritos;
            this.cupoDisponible = cupoDisponible;
        }
    }
}