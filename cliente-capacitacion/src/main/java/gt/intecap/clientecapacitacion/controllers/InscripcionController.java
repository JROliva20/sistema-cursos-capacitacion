package gt.intecap.clientecapacitacion.controllers;

import gt.intecap.clientecapacitacion.dto.InscripcionDto;
import gt.intecap.clientecapacitacion.service.CursoService;
import gt.intecap.clientecapacitacion.service.InscripcionService;
import gt.intecap.clientecapacitacion.service.ParticipanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final CursoService cursoService;
    private final ParticipanteService participanteService;

    public InscripcionController(InscripcionService inscripcionService,
                                 CursoService cursoService,
                                 ParticipanteService participanteService) {
        this.inscripcionService = inscripcionService;
        this.cursoService = cursoService;
        this.participanteService = participanteService;
    }

    @GetMapping
    public String listarInscripciones(Model model) {
        try {
            List<InscripcionDto> inscripciones = inscripcionService.listarInscripciones();
            model.addAttribute("inscripciones", inscripciones);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo conectar con la API de inscripciones.");
        }
        return "inscripciones/listarinscripciones";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("inscripcion", new InscripcionDto());
        model.addAttribute("cursos", cursoService.listarCursos());
        model.addAttribute("participantes", participanteService.listarParticipantes());
        return "inscripciones/formularioinscripciones";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            InscripcionDto inscripcion = inscripcionService.obtenerInscripcionPorId(id);
            model.addAttribute("inscripcion", inscripcion);
            model.addAttribute("cursos", cursoService.listarCursos());
            model.addAttribute("participantes", participanteService.listarParticipantes());
            return "inscripciones/formularioinscripciones";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo cargar la inscripción para editar.");
            return "redirect:/inscripciones";
        }
    }

    @PostMapping("/guardar")
    public String guardarInscripcion(@ModelAttribute InscripcionDto inscripcion) {
        inscripcionService.guardarInscripcion(inscripcion);
        return "redirect:/inscripciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarInscripcion(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return "redirect:/inscripciones";
    }
}