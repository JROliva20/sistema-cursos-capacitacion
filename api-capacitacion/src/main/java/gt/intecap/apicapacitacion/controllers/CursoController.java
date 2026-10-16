package gt.intecap.apicapacitacion.controllers;

import gt.intecap.apicapacitacion.models.CursoModel;
import gt.intecap.apicapacitacion.repository.CursoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    @GetMapping
    public List<CursoModel> listarCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoModel> buscarCursoPorId(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoModel> guardarCurso(@Valid @RequestBody CursoModel curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoModel> actualizarCurso(
            @PathVariable Long id,
            @Valid @RequestBody CursoModel c) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setCodigo(c.getCodigo());
                    curso.setNombre(c.getNombre());
                    curso.setDescripcion(c.getDescripcion());
                    curso.setModalidad(c.getModalidad());
                    curso.setCupo(c.getCupo());
                    curso.setFechaInicio(c.getFechaInicio());
                    curso.setActivo(c.getActivo());
                    return ResponseEntity.ok(cursoRepository.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        }
        return ResponseEntity.notFound().build();
    }
}