# BIP - Teste Integrado

Projeto de estudo com arquitetura integrada entre EJB, Spring Boot REST e Angular, com abordagem DDD no frontend.

---

## 🧱 Estrutura do projeto

```
bip-teste-integrado/
├── ejb-module/       # EJB Stateless com JPA e lógica de negócio
├── backend-module/   # Spring Boot REST API
├── frontend/         # Angular 17+ com estrutura DDD
└── db/               # Scripts de banco de dados
```

A arquitetura do projeto seguiu conceitos de DDD, onde todos os arquivos referentes a um domínio específico são armazenados no mesmo local. No caso do projeto Angular, todos os arquivos referentes a beneficios estão na mesma pasta, o que deve facilitar para expansão para um projeto de microfrontend. O mesmo foi feito para os casos de backend e ejb. 

---

## ⚙️ Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose
- Node.js 18+ (via NVM recomendado)
- Angular CLI (`npm install -g @angular/cli`)

---

## 🚀 Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/grsoliveira/bip-teste-integrado.git
cd bip-teste-integrado
```

### 2. Subir o EJB (WildFly + H2)

```bash
cd ejb-module
sh run.sh
```

> O WildFly sobe na porta `8080` com o H2 em memória. Aguarde a mensagem `WildFly started`.

### 3. Subir o Backend (Spring Boot)

```bash
cd backend-module
./mvnw spring-boot:run
```

> A API REST estará disponível em `http://localhost:8080/api/v1`

### 4. Subir o Frontend (Angular)

```bash
cd frontend/beneficios-app
npm install
ng serve
```

> Acesse `http://localhost:4200`

---

## 📖 Documentação da API

Com o backend rodando, acesse o Swagger em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐛 Issues e melhorias

Acompanhe as tarefas, bugs e próximos passos do projeto na aba de Issues do GitHub:

👉 [github.com/grsoliveira/bip-teste-integrado/issues](https://github.com/grsoliveira/bip-teste-integrado/issues)

---

## 🛠️ Tecnologias

- **EJB 3** — lógica de negócio e persistência com JPA/Hibernate
- **WildFly 39** — servidor de aplicação Jakarta EE
- **Spring Boot 3** — camada REST
- **Angular 17** — frontend com signals e standalone components
- **H2** — banco de dados em memória
- **Docker** — containerização do EJB
- **Swagger / SpringDoc** — documentação da API
