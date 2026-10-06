package com.clinica.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name="medico")
public class Medico {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank private String nome;
  @NotBlank @Column(unique=true) private String cpf;
  private String telefone;
  @Email private String email;
  @NotBlank @Column(unique=true) private String crm;
  private String especialidade;
  @OneToOne(cascade=CascadeType.ALL) @JoinColumn(name="usuario_id") private Usuario usuario;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getNome(){return nome;} public void setNome(String v){nome=v;}
  public String getCpf(){return cpf;} public void setCpf(String v){cpf=v;}
  public String getTelefone(){return telefone;} public void setTelefone(String v){telefone=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public Usuario getUsuario(){return usuario;} public void setUsuario(Usuario v){usuario=v;}
  public String getCrm(){return crm;} public void setCrm(String v){crm=v;}
  public String getEspecialidade(){return especialidade;} public void setEspecialidade(String v){especialidade=v;}
}
