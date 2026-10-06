package com.clinica.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity @Table(name="prontuario")
public class Prontuario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotNull @ManyToOne @JoinColumn(name="paciente_id") private Paciente paciente;
  @NotNull @ManyToOne @JoinColumn(name="medico_id") private Medico medico;
  @OneToOne @JoinColumn(name="consulta_id") private Consulta consulta;
  @Column(name="data_registro") private LocalDateTime dataRegistro = LocalDateTime.now();
  @Column(columnDefinition="TEXT") private String queixa;
  @Column(columnDefinition="TEXT") private String diagnostico;
  @Column(columnDefinition="TEXT") private String prescricao;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public Paciente getPaciente(){return paciente;} public void setPaciente(Paciente v){paciente=v;}
  public Medico getMedico(){return medico;} public void setMedico(Medico v){medico=v;}
  public Consulta getConsulta(){return consulta;} public void setConsulta(Consulta v){consulta=v;}
  public LocalDateTime getDataRegistro(){return dataRegistro;} public void setDataRegistro(LocalDateTime v){dataRegistro=v;}
  public String getQueixa(){return queixa;} public void setQueixa(String v){queixa=v;}
  public String getDiagnostico(){return diagnostico;} public void setDiagnostico(String v){diagnostico=v;}
  public String getPrescricao(){return prescricao;} public void setPrescricao(String v){prescricao=v;}
}
