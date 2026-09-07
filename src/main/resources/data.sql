-- Script SQL para criação do banco de dados da API de Gestão de Veículos
-- Execute este script no PostgreSQL antes de rodar a aplicação

-- Criar banco de dados
CREATE DATABASE api_veiculos;

-- Conectar no banco (no psql: \c api_veiculos)

-- Criar tabelas
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculos (
    id BIGSERIAL PRIMARY KEY,
    marca VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    ano INTEGER NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    valor_diaria DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL'
);

CREATE TABLE IF NOT EXISTS alugueis (
    id BIGSERIAL PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    veiculo_id BIGINT NOT NULL REFERENCES veiculos(id),
    cliente_id BIGINT NOT NULL REFERENCES clientes(id)
);

-- Criar índices para performance
CREATE INDEX IF NOT EXISTS idx_veiculos_placa ON veiculos(placa);
CREATE INDEX IF NOT EXISTS idx_clientes_cpf ON clientes(cpf);
CREATE INDEX IF NOT EXISTS idx_alugueis_veiculo ON alugueis(veiculo_id);
CREATE INDEX IF NOT EXISTS idx_alugueis_cliente ON alugueis(cliente_id);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Inserir usuário de teste (senha: 123456 - hash BCrypt)
INSERT INTO users (email, senha) VALUES 
('admin@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy')
ON CONFLICT (email) DO NOTHING;

-- Inserir dados de teste
INSERT INTO clientes (cpf, nome, telefone) VALUES 
('12345678901', 'João Silva', '11999998888'),
('98765432100', 'Maria Santos', '11888887777')
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO veiculos (marca, modelo, ano, placa, valor_diaria, status) VALUES 
('Toyota', 'Corolla', 2024, 'ABC1D23', 150.00, 'DISPONIVEL'),
('Honda', 'Civic', 2023, 'XYZ9W87', 130.00, 'DISPONIVEL'),
('Ford', 'Focus', 2022, 'DEF4E56', 110.00, 'MANUTENCAO')
ON CONFLICT (placa) DO NOTHING;
