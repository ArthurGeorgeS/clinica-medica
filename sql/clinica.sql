-- Script SQL - Sistema de Clínica (MySQL 8)
DROP DATABASE IF EXISTS clinica;
CREATE DATABASE clinica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica;

CREATE TABLE usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(60) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,           -- hash BCrypt
  perfil ENUM('ADMIN','MEDICO','FUNCIONARIO','PACIENTE') NOT NULL,
  ativo BIT(1) NOT NULL DEFAULT 1
);

CREATE TABLE paciente (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(14) NOT NULL UNIQUE,
  telefone VARCHAR(255), email VARCHAR(255),
  data_nascimento DATE, endereco VARCHAR(255), convenio VARCHAR(255),
  usuario_id BIGINT UNIQUE,
  CONSTRAINT fk_pac_usu FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE medico (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(14) NOT NULL UNIQUE,
  crm VARCHAR(20) NOT NULL UNIQUE,
  especialidade VARCHAR(255),
  telefone VARCHAR(255), email VARCHAR(255),
  usuario_id BIGINT UNIQUE,
  CONSTRAINT fk_med_usu FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE funcionario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(14) NOT NULL UNIQUE,
  cargo VARCHAR(255), data_admissao DATE,
  telefone VARCHAR(255), email VARCHAR(255),
  usuario_id BIGINT UNIQUE,
  CONSTRAINT fk_fun_usu FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE consulta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  paciente_id BIGINT NOT NULL,
  medico_id BIGINT NOT NULL,
  data_hora DATETIME(6) NOT NULL,
  status ENUM('AGENDADA','CONFIRMADA','REALIZADA','CANCELADA') NOT NULL DEFAULT 'AGENDADA',
  observacao VARCHAR(255),
  CONSTRAINT fk_con_pac FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE,
  CONSTRAINT fk_con_med FOREIGN KEY (medico_id) REFERENCES medico(id) ON DELETE CASCADE,
  CONSTRAINT uq_med_horario UNIQUE (medico_id, data_hora)
);

CREATE TABLE prontuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  paciente_id BIGINT NOT NULL,
  medico_id BIGINT NOT NULL,
  consulta_id BIGINT UNIQUE,
  data_registro DATETIME(6),
  queixa TEXT, diagnostico TEXT, prescricao TEXT,
  CONSTRAINT fk_pro_pac FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE,
  CONSTRAINT fk_pro_med FOREIGN KEY (medico_id) REFERENCES medico(id) ON DELETE CASCADE,
  CONSTRAINT fk_pro_con FOREIGN KEY (consulta_id) REFERENCES consulta(id) ON DELETE SET NULL
);

-- Dados iniciais (senha de todos: 123456)
INSERT INTO usuario (login, senha, perfil) VALUES
 ('admin',   '$2a$10$J.f/468Gogoi8cZ7jKG51eFenrWI7ZOC8ByvejEqpBnY0SeqLGv6W', 'ADMIN'),
 ('drjoao',  '$2a$10$J.f/468Gogoi8cZ7jKG51eFenrWI7ZOC8ByvejEqpBnY0SeqLGv6W', 'MEDICO'),
 ('maria',   '$2a$10$J.f/468Gogoi8cZ7jKG51eFenrWI7ZOC8ByvejEqpBnY0SeqLGv6W', 'FUNCIONARIO'),
 ('ana',     '$2a$10$J.f/468Gogoi8cZ7jKG51eFenrWI7ZOC8ByvejEqpBnY0SeqLGv6W', 'PACIENTE');
INSERT INTO medico (nome, cpf, crm, especialidade, telefone, email, usuario_id) VALUES
 ('Dr. João Silva','111.111.111-11','CRM-SP 12345','Cardiologia','(11) 99999-0001','joao@clinica.com',2);
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, usuario_id) VALUES
 ('Maria Souza','222.222.222-22','Recepcionista','2024-02-01',3);
INSERT INTO paciente (nome, cpf, telefone, email, data_nascimento, convenio, usuario_id) VALUES
 ('Ana Pereira','333.333.333-33','(11) 98888-0002','ana@email.com','1990-05-10','Unimed',4);
INSERT INTO consulta (paciente_id, medico_id, data_hora, status) VALUES (1,1,'2026-10-20 09:00:00','AGENDADA');
INSERT INTO prontuario (paciente_id, medico_id, consulta_id, data_registro, queixa, diagnostico, prescricao)
 VALUES (1,1,1,NOW(),'Dor no peito leve','Sem alterações no ECG','Repouso e retorno em 30 dias');
