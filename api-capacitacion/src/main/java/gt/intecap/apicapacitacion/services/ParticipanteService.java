package gt.intecap.apicapacitacion.services;

import gt.intecap.apicapacitacion.models.ParticipanteModel;
import gt.intecap.apicapacitacion.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public List<ParticipanteModel> listarParticipantes() {
        return participanteRepository.findAll();
    }

    public Optional<ParticipanteModel> buscarPorId(Long id) {
        return participanteRepository.findById(id);
    }

    public ParticipanteModel guardarParticipante(ParticipanteModel participante) {
        Optional<ParticipanteModel> existente = participanteRepository.findByCorreo(participante.getCorreo());
        if (existente.isPresent() && !existente.get().getId().equals(participante.getId())) {
            throw new IllegalArgumentException("Ya existe un participante registrado con el correo: " + participante.getCorreo());
        }
        return participanteRepository.save(participante);
    }

    public void eliminarParticipante(Long id) {
        participanteRepository.deleteById(id);
    }
}