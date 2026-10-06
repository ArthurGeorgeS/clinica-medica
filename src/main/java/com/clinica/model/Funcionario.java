package com.clinica.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name="funcionario")
public class Funcionario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank private String nome;
  @NotBlank @Column(unique=true) private String cpf;
  private String telefone;
  @Email private String email;
  private String cargo;
  @Column(name="data_admissao") private LocalDate dataAdmissao;
  @OneToOne(cascade=CascadeType.ALL) @JoinColumn(name="usuario_id") private Usuario usuario;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getNome(){return nome;} public void setNome(String v){nome=v;}
  public String getCpf(){return cpf;} public void setCpf(String v){cpf=v;}
  public String getTelefone(){return telefone;} public void setTelefone(String v){telefone=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public Usuario getUsuario(){return usuario;} public void setUsuario(Usuario v){usuario=v;}
  public String getCargo(){return cargo;} public void setCargo(String v){cargo=v;}
  public LocalDate getDataAdmissao(){return dataAdmissao;} public void setDataAdmissao(LocalDate v){dataAdmissao=v;}
}
