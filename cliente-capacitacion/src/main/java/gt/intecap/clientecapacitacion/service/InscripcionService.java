package gt.intecap.clientecapacitacion.service;

import gt.intecap.clientecapacitacion.dto.InscripcionDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class InscripcionService {

    private final RestClient restClient;

    public InscripcionService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080/api").build();
    }

    public List<InscripcionDto> listarInscripciones() {
        return restClient.get()
                .uri("/inscripciones")
                .retrieve()
                .body(new ParameterizedTypeReference<List<InscripcionDto>>() {});
    }

    public InscripcionDto obtenerInscripcionPorId(Long id) {
        return restClient.get()
                .uri("/inscripciones/{id}", id)
                .retrieve()
                .body(InscripcionDto.class);
    }

    public void guardarInscripcion(InscripcionDto inscripcion) {
        if (inscripcion.getId() != null) {
            restClient.put()
                    .uri("/inscripciones/{id}", inscripcion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(inscripcion)
                    .retrieve()
                    .toBodilessEntity();
        } else {
            restClient.post()
                    .uri("/inscripciones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(inscripcion)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    public void eliminarInscripcion(Long id) {
        restClient.put()
                .uri("/inscripciones/cancelar/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}