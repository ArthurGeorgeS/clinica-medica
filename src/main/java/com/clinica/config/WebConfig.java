package com.clinica.config;
import com.clinica.model.*;
import com.clinica.repository.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/** Converte o id enviado pelos &lt;select&gt; em entidades. */
@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final PacienteRepository p; private final MedicoRepository m; private final ConsultaRepository c;
  public WebConfig(PacienteRepository p, MedicoRepository m, ConsultaRepository c){this.p=p;this.m=m;this.c=c;}
  @Override public void addFormatters(FormatterRegistry r){
    r.addConverter(String.class, Paciente.class, s -> s.isBlank()?null:p.findById(Long.valueOf(s)).orElse(null));
    r.addConverter(String.class, Medico.class, s -> s.isBlank()?null:m.findById(Long.valueOf(s)).orElse(null));
    r.addConverter(String.class, Consulta.class, s -> s.isBlank()?null:c.findById(Long.valueOf(s)).orElse(null));
  }
}
