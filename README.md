# API Gestão de Veículos

API RESTful para gestão de frota e sistema de aluguel de veículos, construída com Spring Boot e PostgreSQL.

## Deploy (Produção)

- **API:** https://api-gestao-veiculos.onrender.com
- **Swagger UI:** https://api-gestao-veiculos.onrender.com/swagger-ui.html
- **OpenAPI JSON:** https://api-gestao-veiculos.onrender.com/v3/api-docs

## Funcionalidades

- CRUD completo de veículos, clientes e aluguéis
- Validação de campos com Bean Validation
- Tratamento global de exceções com status HTTP corretos
- Autenticação JWT (login e registro)
- Documentação Swagger/OpenAPI

## Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Spring Security + JWT
- Springdoc OpenAPI (Swagger)
- Lombok

## Como Rodar

### Pré-requisitos

- Java 21
- PostgreSQL
- Maven

### 1. Criar o banco de dados

```sql
CREATE DATABASE api_veiculos;
```

### 2. Configurar variáveis de ambiente

Edite `src/main/resources/application.properties`:

```properties
# Configuração Local
spring.datasource.url=jdbc:postgresql://localhost:5432/api_veiculos
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

### 4. Acessar

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API:** http://localhost:8080/api

## Endpoints

### Autenticação (Públicos)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/register` | Cadastrar usuário |
| POST | `/api/auth/login` | Fazer login |

### Veículos (Protegidos)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/veiculos` | Cadastrar veículo |
| GET | `/api/veiculos` | Listar veículos |
| GET | `/api/veiculos/{id}` | Buscar por ID |
| GET | `/api/veiculos/placa/{placa}` | Buscar por placa |
| PUT | `/api/veiculos/{id}` | Editar veículo |
| DELETE | `/api/veiculos/{id}` | Remover veículo |

### Clientes (Protegidos)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/clientes` | Cadastrar cliente |
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Buscar por ID |
| GET | `/api/clientes/cpf/{cpf}` | Buscar por CPF |
| PUT | `/api/clientes/{id}` | Editar cliente |
| DELETE | `/api/clientes/{id}` | Remover cliente |

### Aluguéis (Protegidos)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/alugueis` | Registrar aluguel |
| GET | `/api/alugueis` | Listar aluguéis |
| GET | `/api/alugueis/{id}` | Buscar por ID |
| GET | `/api/alugueis/veiculo/{id}` | Buscar por veículo |
| GET | `/api/alugueis/cliente/{id}` | Buscar por cliente |

## Como Usar

### 1. Cadastrar usuário

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "user@email.com", "senha": "123456"}'
```

### 2. Fazer login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@email.com", "senha": "123456"}'
```

Retorna:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 3. Usar o token

Adicione o token no header de todas as requisições:

```bash
curl -X GET http://localhost:8080/api/veiculos \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

## Regras de Negócio

- Veículos só podem ser alugados quando estão com status "DISPONIVEL"
- Data de fim do aluguel não pode ser anterior à data de início
- Aluguel deve ter no mínimo 1 dia
- Valor total é calculado automaticamente (diária × dias)
- CPF e placa devem ser únicos no sistema
- Não é possível remover veículo/cliente vinculado a aluguéis (409)
- Buscar aluguéis por veículo/cliente inexistente retorna 404

## Deploy

### Render (produção atual)

- **Web Service:** Docker (`./Dockerfile`, imagem `maven:3.9-eclipse-temurin-21`), plano Free, região Oregon
- **Banco:** PostgreSQL no Render (`api-veiculos-db`)
- **Variáveis de ambiente:**
  - `SPRING_PROFILES_ACTIVE` = `prod`
  - `SPRING_DATASOURCE_URL` = `jdbc:postgresql://HOST:5432/api_veiculos` (sem usuário/senha na URL)
  - `SPRING_DATASOURCE_USERNAME` = usuário do banco
  - `SPRING_DATASOURCE_PASSWORD` = senha do banco
  - `JWT_SECRET` = chave secreta JWT
- Perfil `prod` em `src/main/resources/application-prod.properties` (`ddl-auto=update`, dialect PostgreSQL).

## Estrutura do Projeto

```
src/main/java/com/alugel/api_gestao_veiculos/
├── config/
│   ├── CorsConfig.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── exceptions/
│       ├── AluguelNaoEncontradoException.java
│       ├── ClienteNaoEncontradoException.java
│       ├── DadosInvalidosException.java
│       ├── DataInvalidaException.java
│       ├── RegistroVinculadoException.java
│       ├── VeiculoIndisponivelException.java
│       └── VeiculoNaoEncontradoException.java
├── modules/
│   ├── aluguel/
│   │   ├── Aluguel.java
│   │   ├── AluguelController.java
│   │   ├── AluguelDTO.java
│   │   ├── AluguelRepository.java
│   │   └── AluguelService.java
│   ├── auth/
│   │   ├── AuthController.java
│   │   ├── AuthDTO.java
│   │   ├── User.java
│   │   ├── UserDetailsImpl.java
│   │   ├── UserDetailsServiceImpl.java
│   │   └── UserRepository.java
│   ├── cliente/
│   │   ├── Cliente.java
│   │   ├── ClienteController.java
│   │   ├── ClienteDTO.java
│   │   ├── ClienteRepository.java
│   │   └── ClienteService.java
│   └── veiculo/
│       ├── StatusVeiculo.java
│       ├── Veiculo.java
│       ├── VeiculoController.java
│       ├── VeiculoDTO.java
│       ├── VeiculoRepository.java
│       └── VeiculoService.java
└── ApiGestaoVeiculosApplication.java
```

## Autor

Luiz Otávio
