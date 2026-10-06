package com.clinica.controller;
import com.clinica.model.*;
import com.clinica.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/consultas")
public class ConsultaController {
  private final ConsultaRepository repo; private final PacienteRepository pacientes; private final MedicoRepository medicos; private final ConsultaRepository consultas;
  public ConsultaController(ConsultaRepository repo, PacienteRepository p, MedicoRepository md, ConsultaRepository c){ this.repo=repo; pacientes=p; medicos=md; consultas=c; }
  @GetMapping public String listar(Model m){ m.addAttribute("itens", repo.findAll()); return "consultas/list"; }
  @GetMapping("/novo") public String novo(Model m){ m.addAttribute("item", new Consulta()); combos(m); return "consultas/form"; }
  @GetMapping("/editar/{id}") public String editar(@PathVariable Long id, Model m){
    m.addAttribute("item", repo.findById(id).orElseThrow()); combos(m); return "consultas/form"; }
  @PostMapping("/salvar") public String salvar(@Valid @ModelAttribute("item") Consulta item, BindingResult br, Model m){
    if (br.hasErrors()) { combos(m); return "consultas/form"; }
    repo.save(item); return "redirect:/consultas";
  }
  @GetMapping("/excluir/{id}") public String excluir(@PathVariable Long id){ repo.deleteById(id); return "redirect:/consultas"; }
  private void combos(Model m){ m.addAttribute("pacientes", pacientes.findAll()); m.addAttribute("medicos", medicos.findAll()); m.addAttribute("consultas", consultas.findAll()); }
}
