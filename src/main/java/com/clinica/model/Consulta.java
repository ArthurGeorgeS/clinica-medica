package com.clinica.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
@Entity @Table(name="consulta")
public class Consulta {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotNull @ManyToOne @JoinColumn(name="paciente_id") private Paciente paciente;
  @NotNull @ManyToOne @JoinColumn(name="medico_id") private Medico medico;
  @NotNull @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) @Column(name="data_hora") private LocalDateTime dataHora;
  @Enumerated(EnumType.STRING) private Status status = Status.AGENDADA;
  private String observacao;
  public enum Status { AGENDADA, CONFIRMADA, REALIZADA, CANCELADA }
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public Paciente getPaciente(){return paciente;} public void setPaciente(Paciente v){paciente=v;}
  public Medico getMedico(){return medico;} public void setMedico(Medico v){medico=v;}
  public LocalDateTime getDataHora(){return dataHora;} public void setDataHora(LocalDateTime v){dataHora=v;}
  public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
  public String getObservacao(){return observacao;} public void setObservacao(String v){observacao=v;}
}
