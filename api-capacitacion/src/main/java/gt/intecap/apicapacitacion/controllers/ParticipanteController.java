package gt.intecap.apicapacitacion.controllers;

import gt.intecap.apicapacitacion.models.ParticipanteModel;
import gt.intecap.apicapacitacion.services.ParticipanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @GetMapping
    public List<ParticipanteModel> listarParticipantes() {
        return participanteService.listarParticipantes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteModel> buscarParticipantePorId(@PathVariable Long id) {
        return participanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardarParticipante(@Valid @RequestBody ParticipanteModel participante) {
        try {
            ParticipanteModel nuevoParticipante = participanteService.guardarParticipante(participante);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoParticipante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarParticipante(
            @PathVariable Long id,
            @Valid @RequestBody ParticipanteModel p) {
        return participanteService.buscarPorId(id)
                .map(participante -> {
                    participante.setNombres(p.getNombres());
                    participante.setApellidos(p.getApellidos());
                    participante.setCorreo(p.getCorreo());
                    participante.setTelefono(p.getTelefono());
                    participante.setProfesion(p.getProfesion());
                    try {
                        return ResponseEntity.ok(participanteService.guardarParticipante(participante));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarParticipante(@PathVariable Long id) {
        if (participanteService.buscarPorId(id).isPresent()) {
            participanteService.eliminarParticipante(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}