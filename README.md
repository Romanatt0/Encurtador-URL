# Encurtador de URL

Este é um projeto de encurtador de URL desenvolvido com Spring Boot (Java).

## ⚠️ Em Desenvolvimento
Este projeto **ainda está em desenvolvimento**. As funcionalidades e endpoints podem mudar sem aviso prévio.

## Descrição
A proposta do sistema é fornecer uma API capaz de receber URLs longas e gerar versões curtas ("urls encurtadas") que redirecionam para o endereço original.

- O endpoint principal está em `/dev`.
- Você pode encurtar uma URL via POST em `/dev/encurtar`.
- O redirecionamento é feito via GET em `/dev/{urlEncurtada}`.

### Exemplo de uso (apenas para teste)
- **POST** `/dev/encurtar`
    - Body JSON:
    ```json
    {
      "url": "https://meusite.com",
      "urlEncurtada": "abc123"
    }
    ```
- **GET** `/dev/abc123`
    - Redireciona para a URL salva.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3
- Spring Data JPA
- Lombok
- PostgreSQL (pode ser adaptado para outro banco, ajuste em `application.properties`)

## Estrutura Básica
- `controllers/` — Controllers REST dos endpoints principais
- `services/` — Lógica de serviço para encurtar URLs e buscar por URL encurtada
- `models/` — Modelos JPA
- `repositories/` — Repositórios Spring Data
- `DTOs/` — Objetos de transferência de dados
- `exceptions/` — Exceções customizadas

## Como Rodar Localmente
```bash
# Na raiz do projeto
./mvnw spring-boot:run
```
Por padrão, é usado o banco PostgreSQL (conferir dados de acesso em `src/main/resources/application.properties`).

---

Sinta-se livre para contribuir ou sugerir melhorias! Ainda estamos construindo as principais funcionalidades do encurtador.
