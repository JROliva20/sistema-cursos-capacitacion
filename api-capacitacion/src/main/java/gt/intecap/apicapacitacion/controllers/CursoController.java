package gt.intecap.apicapacitacion.controllers;

import gt.intecap.apicapacitacion.models.CursoModel;
import gt.intecap.apicapacitacion.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<CursoService.CursoConDetalleDto> listarCursos() {
        return cursoService.listarCursosConDetalle();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoModel> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardarCurso(@RequestBody CursoModel curso) {
        try {
            CursoModel nuevoCurso = cursoService.guardarCurso(curso);
            return ResponseEntity.ok(nuevoCurso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        try {
            cursoService.eliminarCurso(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}