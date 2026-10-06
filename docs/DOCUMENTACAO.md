# Documentação – Sistema de Gestão de Clínica

## 1. Objetivo
Gerenciar cadastro de pacientes, médicos e funcionários, agendamento de consultas e prontuários, com acesso restrito a usuários autenticados.

## 2. Requisitos funcionais
| ID | Requisito |
|----|-----------|
| RF01 | Manter (CRUD) pacientes |
| RF02 | Manter (CRUD) médicos |
| RF03 | Manter (CRUD) funcionários |
| RF04 | Manter (CRUD) agendamentos de consultas |
| RF05 | Manter (CRUD) prontuários |
| RF06 | Permitir acesso somente com login e senha |
| RF07 | Usuário sem acesso pode se cadastrar (como paciente); médicos/funcionários recebem login ao serem cadastrados por funcionário/admin |

## 3. Requisitos não funcionais
- Java 17, Spring Boot 3, Thymeleaf (HTML), MySQL 8.
- Senhas armazenadas com BCrypt.
- Controle de acesso por perfil (Spring Security).

## 4. Perfis e permissões
| Tela | ADMIN | FUNCIONÁRIO | MÉDICO | PACIENTE |
|------|:---:|:---:|:---:|:---:|
| Pacientes | ✔ | ✔ | ✔ | |
| Médicos | ✔ | ✔ | | |
| Funcionários | ✔ | ✔ | | |
| Consultas | ✔ | ✔ | ✔ | ✔ |
| Prontuários | ✔ | | ✔ | |

## 5. Casos de uso
Ver `diagrama-caso-de-uso.svg`.

- **UC01 Fazer login** – Ator informa login/senha; sistema valida e direciona à tela inicial. Exceção: credenciais inválidas → mensagem de erro.
- **UC02 Cadastrar-se** – Usuário sem acesso preenche dados pessoais + login/senha; sistema cria paciente e usuário (perfil PACIENTE). Exceção: login já existe.
- **UC03 Manter pacientes / UC04 médicos / UC05 funcionários** – Listar, incluir, editar, excluir; opcionalmente criar/alterar login de acesso.
- **UC06 Manter consultas** – Agendar escolhendo paciente, médico e data/hora; alterar status (AGENDADA, CONFIRMADA, REALIZADA, CANCELADA); excluir. Regra: um médico não pode ter duas consultas no mesmo horário.
- **UC07 Manter prontuários** – Médico registra queixa, diagnóstico e prescrição vinculados a paciente (e opcionalmente à consulta).
- **UC08 Sair** – Encerra a sessão.

## 6. Modelo de dados
`usuario (1)—(0..1) paciente | medico | funcionario`
`paciente (1)—(N) consulta (N)—(1) medico`
`consulta (1)—(0..1) prontuario`; prontuário referencia paciente e médico.
Script completo em `sql/clinica.sql`.

## 7. Arquitetura
```
Navegador (HTML/Thymeleaf) → Controller → Repository (Spring Data JPA) → MySQL
                     ↑ Spring Security (login por formulário, perfis)
```
