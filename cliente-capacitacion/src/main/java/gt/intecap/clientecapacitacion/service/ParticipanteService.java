package gt.intecap.clientecapacitacion.service;

import gt.intecap.clientecapacitacion.dto.ParticipanteDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ParticipanteService {

    private final RestClient restClient;

    public ParticipanteService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080/api").build();
    }

    public List<ParticipanteDto> listarParticipantes() {
        return restClient.get()
                .uri("/participantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ParticipanteDto>>() {});
    }

    public ParticipanteDto obtenerParticipantePorId(Long id) {
        return restClient.get()
                .uri("/participantes/{id}", id)
                .retrieve()
                .body(ParticipanteDto.class);
    }

    public void guardarParticipante(ParticipanteDto participante) {
        if (participante.getId() != null) {
            restClient.put()
                    .uri("/participantes/{id}", participante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(participante)
                    .retrieve()
                    .toBodilessEntity();
        } else {
            restClient.post()
                    .uri("/participantes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(participante)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    public void eliminarParticipante(Long id) {
        restClient.delete()
                .uri("/participantes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}