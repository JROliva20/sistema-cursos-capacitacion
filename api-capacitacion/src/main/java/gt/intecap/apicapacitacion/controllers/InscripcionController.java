package gt.intecap.apicapacitacion.controllers;

import gt.intecap.apicapacitacion.models.InscripcionModel;
import gt.intecap.apicapacitacion.services.InscripcionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @GetMapping
    public List<InscripcionModel> listarInscripciones() {
        return inscripcionService.listarInscripciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionModel> obtenerInscripcionPorId(@PathVariable Long id) {
        return inscripcionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrarInscripcion(@RequestBody InscripcionRequestDto request) {
        try {
            InscripcionModel nuevaInscripcion = inscripcionService.registrarInscripcion(request.getCursoId(), request.getParticipanteId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInscripcion(@PathVariable Long id, @RequestBody InscripcionRequestDto request) {
        try {
            InscripcionModel actualizada = inscripcionService.actualizarInscripcion(id, request.getCursoId(), request.getParticipanteId(), request.getFechaInscripcion());
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long id) {
        try {
            inscripcionService.cancelarInscripcion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Data
    public static class InscripcionRequestDto {
        private Long cursoId;
        private Long participanteId;
        private LocalDate fechaInscripcion;
    }
}