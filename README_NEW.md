# 📊 Simulador Financeiro

Aplicação desenvolvida para simular operações financeiras (juros compostos) e expor um endpoint REST para criação e consulta de simulações.

Funcionalidades principais:

- Simulação de juros compostos com memória mensal (saldo inicial, juros do mês, saldo final).
- Persistência das simulações em banco H2 (em memória, para dev/test).
- Endpoints REST para criar e consultar simulações (POST/GET).
- Validação de entrada via Jakarta Bean Validation.

---

## 🚀 Tecnologias Utilizadas

- Java 25 (requisito do projeto)
- Quarkus Framework
- Jakarta EE (JAX-RS, CDI, Validation, JPA)
- Hibernate ORM
- H2 Database (desenvolvimento/testes)
- Maven
- Lombok (reduz boilerplate — configurado no POM)

---

## 📁 Pré-requisitos

- Java 25 (conforme requisito do projeto)
- Maven 3.8+

> Observação sobre cobertura (JaCoCo): JaCoCo pode não suportar instrumentação direta de bytecode gerado por Java 25. Por isso a geração de cobertura foi colocada como opt-in (veja seção "Cobertura de Testes").

---

## ▶️ Rodando o Projeto (desenvolvimento)

```bash
# rodar em modo dev (hot-reload)
./mvnw quarkus:dev
```

A aplicação estará disponível em: http://localhost:8080

---

## 📄 Documentação da API (OpenAPI / Swagger)

A documentação interativa está disponível em:

```
http://localhost:8080/q/swagger-ui/
```

---

## 📦 Endpoints principais (exemplos)

### Criar Simulação

```http
POST /api/simulacao
```

Body Exemplo (JSON):

```json
{
  "valorInicial": 1000.00,
  "taxaJurosMensal": 1.2,
  "prazoMeses": 12
}
```

Resposta: 201 Created com Location `/api/simulacao/{id}` e o corpo com `id`, `valorTotal`, `valorTotalJuros` e `memoriaCalculo` (lista mensal).

---

### Consultar Simulação

```http
GET /api/simulacao/{id}
```

Resposta:
- 200 com payload da simulação
- 404 se não encontrado

---

## 🧪 Testes

O projeto contém testes unitários e de integração (QuarkusTest).

### Rodando os testes

```bash
./mvnw test
```

### Cobertura de Testes (opt-in)

JaCoCo foi movido para um profile Maven chamado `coverage` porque a instrumentação pode falhar com Java 25. Para gerar relatórios de cobertura, execute em um ambiente com JDK compatível com o agente JaCoCo (por exemplo, JDK 21) ou ajuste seu CI para usar JDK 21 apenas para a etapa de cobertura.

Exemplo (em CI ou local com JDK 21):

```bash
# executa verify com JaCoCo ativo
./mvnw -Pcoverage verify
```

O relatório será gerado em `target/site/jacoco/index.html` quando o profile `coverage` for executado com sucesso.

---

## 🐳 Executando com Docker

### 1. Gerar o JAR da aplicação

```bash
./mvnw clean package -DskipTests
```

### 2. Construir a imagem Docker

```bash
docker build -f src/main/docker/Dockerfile.jvm -t simulador-financeiro .
```

### 3. Executar o container

```bash
docker run -i --rm -p 8080:8080 simulador-financeiro
```

A aplicação estará disponível em: http://localhost:8080

---

## 🔐 Melhorias Futuras / Observações

- Adicionar autenticação e autorização (JWT/OIDC).
- Persistência em banco relacional externo (Postgres, Oracle, etc.).
- Adicionar mapeamento de erros/ExceptionMapper para respostas padronizadas (400/404/500).
- Habilitar cobertura contínua em CI usando um runner JDK 21 para JaCoCo.

---

## 📬 Postman / Collection

Se você quiser, posso adicionar uma `docs/postman` com uma collection com os principais endpoints para importação no Postman.

---

## 🧭 Como contribuir

1. Crie uma branch com seu feature/fix
2. Abra um Pull Request apontando para `main`
3. Inclua testes quando alterar comportamento

---

Se quiser, eu adiciono também um exemplo de workflow GitHub Actions que executa testes e cobertura usando JDK 21 (para o passo de cobertura). Basta pedir que eu gere o arquivo em `.github/workflows/`.
