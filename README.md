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

---

## 🔐 Melhorias Futuras / Observações

- Adicionar autenticação e autorização (JWT/OIDC).
- Adicionar mapeamento de erros/ExceptionMapper para respostas padronizadas (400/404/500).

---

## 📬 Postman / Collection

A collection atualizada está disponível em:

```
docs/postman/Simulacoes_API_Atualizada.postman_collection.json
```

Pronta para importação e uso no Postman.

---

