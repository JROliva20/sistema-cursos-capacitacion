package gt.intecap.clientecapacitacion.controllers;

import gt.intecap.clientecapacitacion.dto.CursoDto;
import gt.intecap.clientecapacitacion.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listarCursos(Model model) {
        try {
            List<CursoDto> cursos = cursoService.listarCursos();
            model.addAttribute("cursos", cursos);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo conectar con la API.");
        }
        return "cursos/listarcursos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("curso", new CursoDto());
        return "cursos/formulariocursos";
    }

    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute CursoDto curso) {
        cursoService.guardarCurso(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CursoDto curso = cursoService.obtenerCursoPorId(id);
        model.addAttribute("curso", curso);
        return "cursos/formulariocursos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return "redirect:/cursos";
    }
}