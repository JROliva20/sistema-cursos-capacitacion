package gt.intecap.clientecapacitacion.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String profesion;
}