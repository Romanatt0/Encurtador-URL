# Encurtador de URL

API REST desenvolvida com Spring Boot para encurtar URLs, gerar QR Codes e contabilizar métricas de acesso.

## Funcionalidades

- **Encurtar URLs** — transforma URLs longas em códigos curtos com data de expiração automática (3 meses)
- **Redirecionamento** — ao acessar a URL encurtada, o usuário é redirecionado para a URL original
- **Geração de QR Code** — gera imagens PNG de QR Codes que apontam para a URL encurtada, contabilizando acessos ao escanear
- **Métricas de acesso** — contabiliza e consulta acessos por dia, mês e ano

## Tecnologias

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- PostgreSQL
- Google ZXing (geração de QR Code)
- Lombok
- Maven
- Docker

## Endpoints

### URL

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/dev/encurtar` | Encurta uma URL |
| `GET` | `/dev/{codigo}` | Redireciona para a URL original |

**Request body** (POST /dev/encurtar):
```json
{
  "url": "https://www.exemplo.com/pagina-muito-longa",
  "urlEncurtada": "meuCodigo"
}
```

**Response**:
```json
{
  "url": "https://www.exemplo.com/pagina-muito-longa",
  "urlEncurtada": "meuCodigo",
  "dataExpiracao": "2026-07-12"
}
```

### QR Code

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/qrcode/{codigo}` | Retorna uma imagem PNG do QR Code |

O QR Code gerado contém a URL completa de redirecionamento (ex: `http://host:porta/dev/{codigo}`), permitindo que ao escanear o acesso seja contabilizado nas métricas.

### Métricas

| Método | Endpoint | Parâmetros | Descrição |
|--------|----------|------------|-----------|
| `GET` | `/api/metrics/{codigo}/dia` | `?dia=&mes=&ano=` | Acessos em um dia específico |
| `GET` | `/api/metrics/{codigo}/hoje` | — | Acessos no dia atual |
| `GET` | `/api/metrics/{codigo}/mes` | `?mes=&ano=` | Total de acessos no mês |
| `GET` | `/api/metrics/{codigo}/ano` | `?ano=` | Total de acessos no ano |

**Exemplo de response**:
```json
{
  "urlEncurtada": "meuCodigo",
  "dia": 12,
  "mes": 4,
  "ano": 2026,
  "quantidade": 15
}
```

Nos endpoints de mês e ano, os campos de menor granularidade retornam `null` (ex: consulta por ano retorna `dia: null, mes: null`).

## Como rodar localmente

### Pré-requisitos

- Java 21
- PostgreSQL

### Passos

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd encurtador
```

2. Configure o banco de dados no `application.properties`

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Deploy com Docker

### Build e execução local:

```bash
docker build -t encurtador .
docker run -p 8080:8080 encurtador
```

### Deploy no Render

1. Crie um **Web Service** no Render apontando para o repositório
2. O Render detecta o `Dockerfile` automaticamente
3. Defina a variável de ambiente `PORT=8080` nas configurações do serviço

## Estrutura do projeto

```
src/main/java/com/url/encurtador/
├── controllers/
│   ├── UrlController.java        # Endpoints de encurtar e redirecionar
│   ├── MetricController.java     # Endpoints de métricas
│   ├── QRCodeController.java     # Endpoint de geração de QR Code
│   └── RefreshController.java    # Health-check
├── services/
│   ├── UrlService.java           # Lógica de encurtamento
│   ├── UrlMetricService.java     # Lógica de métricas
│   └── QRCodeService.java        # Geração de QR Code
├── models/
│   ├── UrlModel.java             # Entidade URL encurtada
│   └── UrlMetricModel.java       # Entidade métricas de acesso
├── repositories/
│   ├── UrlRepository.java
│   └── UrlMetricRepository.java
├── DTOs/
│   ├── UrlRequestDTO.java
│   ├── UrlResponseDTO.java
│   └── UrlMetricResponseDTO.java
└── EncurtadorApplication.java
```
