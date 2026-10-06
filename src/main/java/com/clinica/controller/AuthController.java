package com.clinica.controller;
import com.clinica.model.*;
import com.clinica.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class AuthController {
  private final UsuarioRepository usuarios; private final PacienteRepository pacientes; private final PasswordEncoder enc;
  public AuthController(UsuarioRepository u, PacienteRepository p, PasswordEncoder e){usuarios=u;pacientes=p;enc=e;}
  @GetMapping("/login") public String login(){ return "login"; }
  @GetMapping("/") public String home(){ return "index"; }
  @GetMapping("/cadastro") public String cadastro(Model m){ m.addAttribute("paciente", new Paciente()); return "cadastro"; }
  /** Usuário sem acesso se cadastra como PACIENTE. Médicos e funcionários são cadastrados pelo ADMIN/FUNCIONÁRIO. */
  @PostMapping("/cadastro")
  public String salvar(@ModelAttribute Paciente paciente, @RequestParam String login, @RequestParam String senha, Model m){
    if (usuarios.existsByLogin(login)) { m.addAttribute("erro","Login já existe"); return "cadastro"; }
    Usuario u = new Usuario(); u.setLogin(login); u.setSenha(enc.encode(senha)); u.setPerfil(Usuario.Perfil.PACIENTE);
    paciente.setUsuario(u); pacientes.save(paciente);
    return "redirect:/login?cadastrado";
  }
}
