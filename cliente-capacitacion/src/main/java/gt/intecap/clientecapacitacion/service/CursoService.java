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
                .uri("/cursos")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CursoDto>>() {});
    }

    public List<CursoDto> listarCursosFiltrados(String nombre, String modalidad) {
        List<CursoDto> cursos = listarCursos();
        if (cursos == null) return List.of();

        return cursos.stream().filter(curso -> {
            boolean cumpleNombre = (nombre == null || nombre.isBlank()) ||
                    (curso.getNombre() != null && curso.getNombre().toLowerCase().contains(nombre.toLowerCase()));

            boolean cumpleModalidad = (modalidad == null || modalidad.isBlank() || modalidad.equals("Todas las modalidades")) ||
                    (curso.getModalidad() != null && curso.getModalidad().equalsIgnoreCase(modalidad));

            return cumpleNombre && cumpleModalidad;
        }).toList();
    }

    public void guardarCurso(CursoDto curso) {
        restClient.post()
                .uri("/cursos")
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