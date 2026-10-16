package gt.intecap.apicapacitacion.controllers;

import gt.intecap.apicapacitacion.models.ParticipanteModel;
import gt.intecap.apicapacitacion.repository.ParticipanteRepository;
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

    private final ParticipanteRepository participanteRepository;

    @GetMapping
    public List<ParticipanteModel> listarParticipantes() {
        return participanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteModel> buscarParticipantePorId(@PathVariable Long id) {
        return participanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParticipanteModel> guardarParticipante(@Valid @RequestBody ParticipanteModel participante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteRepository.save(participante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteModel> actualizarParticipante(
            @PathVariable Long id,
            @Valid @RequestBody ParticipanteModel p) {
        return participanteRepository.findById(id)
                .map(participante -> {
                    participante.setNombres(p.getNombres());
                    participante.setApellidos(p.getApellidos());
                    participante.setCorreo(p.getCorreo());
                    participante.setTelefono(p.getTelefono());
                    participante.setProfesion(p.getProfesion());
                    return ResponseEntity.ok(participanteRepository.save(participante));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarParticipante(@PathVariable Long id) {
        if (participanteRepository.existsById(id)) {
            participanteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}