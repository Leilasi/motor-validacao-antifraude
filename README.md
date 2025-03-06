# 🚀 Motor de Validação Antifraude

Este projeto é um protótipo de motor antifraude, desenvolvido em **Java com Spring Boot**, com o objetivo de validar dados de usuários e atribuir um grau de confiabilidade para prevenção de fraudes em processos internos.

---

## 📌 Funcionalidades

- ✅ Cadastro de usuários com dados pessoais.
- ✅ Validação dos dados informados e atribuição de uma nota de confiabilidade (de 0 a 10).
- ✅ Persistência de dados utilizando banco em memória **H2**.Caso o perfil utilizado seja **test**.
- ✅ Persistência de dados utilizando banco em memória **PostgreSQL**.Caso o perfil utilizado seja **dev**.
- ✅ API REST documentada com **Swagger**.
- ✅ Arquitetura baseada em **Camadas**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Swagger / SpringDoc (OpenAPI)**
- **Maven**
- **Docker**
- **PostgreSQL**

---

## 📄 Documentação da API

Após rodar o projeto, acesse a documentação interativa pelo Swagger:

http://localhost:8080/swagger-ui/index.html


---

## 📦 Como rodar o projeto localmente

### Pré-requisitos:
- Git
- Java 17+
- Maven

### Passo a passo:

```bash
# Clone o repositório
git clone git@github.com:Leilasi/motor-validacao-antifraude.git

# Acesse o diretório do projeto
cd motor-validacao-antifraude

# Compile o projeto
mvn clean install

# Rode a aplicação
mvn spring-boot:run

## 🗂️ Endpoints principais

| Método | Rota                  | Descrição                     |
|--------|-----------------------|-------------------------------|
| POST   | /api/usuarios/salvar  | Valida e cadastra um usuário |

---

## 🔍 Exemplo de Requisição (JSON)

### POST `/api/usuarios/salvar`

```json
{
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "email": "maria@email.com",
  "telefone": "11999999999"
}

✅ Resposta de sucesso:

{
  "mensagem": "Usuário cadastrado com sucesso",
  "grauConfiabilidade": 8
}



🧠 Melhorias futuras
Integração com APIs externas de validação.
Autenticação e autorização.
Deploy em nuvem (AWS/GCP).

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir um **pull request** ou relatar problemas na aba de **issues**.

---

Feito com ❤️ por [Leila Fernanda da Silva]  [Estudante da Engenharia da Computação Univesp] 


