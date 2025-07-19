package pe.isil.saturno_4631.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.saturno_4631.model.Liga;
import pe.isil.saturno_4631.model.Usuario;
import pe.isil.saturno_4631.repository.UsuarioRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    String index (Model model)
    {
        List<Usuario> usuarios;

        usuarios =usuarioRepository.findAll();

        model.addAttribute("usuarios", usuarios);

        return "admin/usuarios/index";
    }

    @GetMapping("/nuevo") //localhost:8080/cursos/nuevo
    String nuevo(Model model)
    {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/nuevo"; //se retorna la vista: nuevo.html
    }

    @PostMapping("/nuevo") //localhost:8080/cursos/nuevo
    String guardar(Model model, Usuario usuario)
    {
        try {
            usuarioRepository.save(usuario);
            return "redirect:/admin/usuarios";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Algo ocurrió.");
            return "admin/usuarios/nuevo"; // volver al formulario
        }
    }

    @PostMapping("/eliminar/{id}")
    String eliminar (@PathVariable Integer id, RedirectAttributes ra)
    {
        usuarioRepository.deleteById(id);
        ra.addFlashAttribute("Usuario Eliminado!!!");
        return "redirect:/admin/usuarios";
    }
}
