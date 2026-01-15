# 📚 Biblioteca API (Backend)

API RESTful desenvolvida em **Java com Spring Boot** para gerenciamento de bibliotecas. O sistema controla acervo, usuários e o fluxo completo de empréstimos com regras de negócio automatizadas.

Projeto desenvolvido como parte do Trabalho de Conclusão de Curso (TCC) em Sistemas de Informação.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3** (Web, Data JPA, Validation, Security)
- **Spring Security + JWT** (Autenticação e Autorização via Token)
- **Swagger / OpenAPI** (Documentação viva da API)
- **H2 Database / MySQL** (Banco de dados)
- **Lombok** (Produtividade)

## ⚙️ Funcionalidades Principais

### 🔐 Autenticação e Segurança
- Login e Cadastro de usuários.
- Autenticação via **Token JWT (Bearer)**.
- Controle de acesso por perfis (**ADMIN** e **CLIENTE**).

### 📖 Gestão de Livros
- CRUD completo (Criar, Listar, Editar, Excluir).
- **Cálculo de Disponibilidade:** A API verifica automaticamente se existe um empréstimo ativo antes de listar o livro como disponível.

### 🔄 Fluxo de Empréstimos (Core)
- **Novo Empréstimo:** Valida se o livro já está emprestado.
- **Devolução:** Atualiza o status e libera o livro.
- **Renovação (+7 dias):**
    - Regra 1: Não permite renovar se estiver **atrasado**.
    - Regra 2: Limite máximo de **3 renovações** consecutivas.
- **Histórico:** Listagem completa para Admins e "Meus Empréstimos" para Clientes.

### 📊 Dashboard Administrativo
- Endpoint exclusivo que retorna KPIs em tempo real:
    - Total de Livros e Leitores.
    - Empréstimos Ativos.
    - **Alertas de Atraso** (Contagem de livros não devolvidos no prazo).

## 📄 Documentação da API

A documentação interativa (Swagger UI) pode ser acessada após rodar o projeto em: http://localhost:8080/docs

## 🛠️ Como Executar

1. Clone o repositório.
2. Configure o banco de dados no `application.properties` (padrão: H2 em memória).
3. Execute o projeto via Maven ou IDE:

```bash
./mvnw spring-boot:run
````

A API estará disponível em http://localhost:8080.

📂 Estrutura do Projeto

config: Configurações de Segurança (SecurityConfig) e Swagger.

controller: Endpoints da API.

service: Regras de negócio (Validações de renovação, datas, etc).

repository: Comunicação com o Banco de Dados.

model: Entidades JPA.

dto: Objetos de transferência de dados (Data Transfer Objects).
