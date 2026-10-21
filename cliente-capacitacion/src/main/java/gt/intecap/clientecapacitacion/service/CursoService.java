package gt.intecap.clientecapacitacion.service;

import gt.intecap.clientecapacitacion.dto.CursoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CursoService {

    private final RestClient restClient;

    public CursoService(@Value("${api.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<CursoDto> listarCursos() {
        return restClient.get()
                .uri("/cursos") // Cambiado a /cursos para que coincida con la API
                .retrieve()
                .body(new ParameterizedTypeReference<List<CursoDto>>() {});
    }

    public void guardarCurso(CursoDto curso) {
        restClient.post()
                .uri("/cursos") // Cambiado a /cursos
                .body(curso)
                .retrieve()
                .toBodilessEntity();
    }

    public CursoDto obtenerCursoPorId(Long id) {
        return restClient.get()
                .uri("/cursos/{id}", id)
                .retrieve()
                .body(CursoDto.class);
    }

    public void eliminarCurso(Long id) {
        restClient.delete()
                .uri("/cursos/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}