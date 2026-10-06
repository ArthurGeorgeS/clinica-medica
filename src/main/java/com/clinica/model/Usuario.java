package com.clinica.model;
import jakarta.persistence.*;
@Entity @Table(name="usuario")
public class Usuario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true,nullable=false) private String login;
  @Column(nullable=false) private String senha;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Perfil perfil;
  private boolean ativo = true;
  public enum Perfil { ADMIN, MEDICO, FUNCIONARIO, PACIENTE }
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getLogin(){return login;} public void setLogin(String v){login=v;}
  public String getSenha(){return senha;} public void setSenha(String v){senha=v;}
  public Perfil getPerfil(){return perfil;} public void setPerfil(Perfil v){perfil=v;}
  public boolean isAtivo(){return ativo;} public void setAtivo(boolean v){ativo=v;}
}
