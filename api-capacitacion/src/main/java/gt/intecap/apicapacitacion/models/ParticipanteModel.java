package gt.intecap.apicapacitacion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "participantes")
public class ParticipanteModel extends PersonaModel {

    @Size(max = 80)
    private String profesion;
}