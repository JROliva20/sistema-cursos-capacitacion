package gt.intecap.clientecapacitacion.controllers;

import gt.intecap.clientecapacitacion.dto.ParticipanteDto;
import gt.intecap.clientecapacitacion.service.ParticipanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping
    public String listarParticipantes(
            @RequestParam(required = false) String nombre,
            Model model) {
        try {
            List<ParticipanteDto> participantes = participanteService.listarParticipantesFiltrados(nombre);
            model.addAttribute("participantes", participantes);

            model.addAttribute("nombreFiltro", nombre);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo conectar con la API de participantes.");
        }
        return "participantes/listarparticipantes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("participante", new ParticipanteDto());
        return "participantes/formularioparticipantes";
    }

    @PostMapping("/guardar")
    public String guardarParticipante(@ModelAttribute ParticipanteDto participante) {
        participanteService.guardarParticipante(participante);
        return "redirect:/participantes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        ParticipanteDto participante = participanteService.obtenerParticipantePorId(id);
        model.addAttribute("participante", participante);
        return "participantes/formularioparticipantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminarParticipante(id);
        return "redirect:/participantes";
    }
}