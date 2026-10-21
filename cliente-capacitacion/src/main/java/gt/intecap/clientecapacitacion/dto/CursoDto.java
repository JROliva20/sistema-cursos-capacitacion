package gt.intecap.clientecapacitacion.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CursoDto {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String modalidad;
    private Integer cupo;
    private LocalDate fechaInicio;
    private Boolean activo;

    private Integer totalInscritos;
    private Integer cupoDisponible;
}