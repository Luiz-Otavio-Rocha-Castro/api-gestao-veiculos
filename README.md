# API Gestão de Veículos

API RESTful para gestão de frota e sistema de aluguel de veículos, construída com Spring Boot e PostgreSQL.

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

- Java 17+
- PostgreSQL
- Maven

### 1. Criar o banco de dados

```sql
CREATE DATABASE api_veiculos;
```

Ou execute o script completo em `src/main/resources/data.sql`.

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

## Deploy

### Render

1. Criar conta no [Render](https://render.com)
2. Criar um **Web Service** conectando ao repositório GitHub
3. Criar um **PostgreSQL** no Render
4. Configurar variáveis de ambiente:
   - `DATABASE_URL` = URL do banco Render
   - `DATABASE_USERNAME` = usuário do banco
   - `DATABASE_PASSWORD` = senha do banco
   - `JWT_SECRET` = chave secreta JWT (mínimo 32 caracteres)

## Estrutura do Projeto

```
src/main/java/com/alugel/api_gestao_veiculos/
├── config/
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
