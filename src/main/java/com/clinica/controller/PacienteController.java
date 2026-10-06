package com.clinica.controller;
import com.clinica.model.*;
import com.clinica.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/pacientes")
public class PacienteController {
  private final PacienteRepository repo; private final PasswordEncoder enc;
  public PacienteController(PacienteRepository repo, PasswordEncoder enc){ this.repo=repo; this.enc=enc; }
  @GetMapping public String listar(Model m){ m.addAttribute("itens", repo.findAll()); return "pacientes/list"; }
  @GetMapping("/novo") public String novo(Model m){ m.addAttribute("item", new Paciente());  return "pacientes/form"; }
  @GetMapping("/editar/{id}") public String editar(@PathVariable Long id, Model m){
    m.addAttribute("item", repo.findById(id).orElseThrow());  return "pacientes/form"; }
  @PostMapping("/salvar") public String salvar(@Valid @ModelAttribute("item") Paciente item, BindingResult br,
      @RequestParam(required=false) String login, @RequestParam(required=false) String senha){
    if (br.hasErrors()) return "pacientes/form";
    if (item.getId()!=null) { repo.findById(item.getId()).ifPresent(old -> item.setUsuario(old.getUsuario())); }
    if (login!=null && !login.isBlank() && senha!=null && !senha.isBlank()) {
      Usuario u = item.getUsuario()!=null ? item.getUsuario() : new Usuario();
      u.setLogin(login); u.setSenha(enc.encode(senha)); u.setPerfil(Usuario.Perfil.PACIENTE); item.setUsuario(u);
    }
    repo.save(item); return "redirect:/pacientes";
  }
  @GetMapping("/excluir/{id}") public String excluir(@PathVariable Long id){ repo.deleteById(id); return "redirect:/pacientes"; }
}
