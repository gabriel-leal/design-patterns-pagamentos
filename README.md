# API de Pagamentos com Design Patterns

Projeto desenvolvido para praticar **Padrões de Projeto (Design Patterns)** com Java e Spring Boot.

A aplicação simula o processamento de pagamentos utilizando:

- PIX
- Cartão de crédito
- Cartão de débito

O objetivo principal não é criar um sistema financeiro real, mas demonstrar de maneira simples e prática a aplicação dos padrões:

- Strategy
- Factory
- Facade
- Singleton

## Tecnologias

- Java 21
- Spring Boot
- Maven
- Bean Validation
- JUnit 5

## Estrutura do projeto

```text
src/main/java/com/gabriel/pagamentos
│
├── controller
│   └── PagamentoController.java
│
├── dto
│   ├── PagamentoRequest.java
│   └── PagamentoResponse.java
│
├── enums
│   ├── FormaPagamento.java
│   └── StatusPagamento.java
│
├── exception
│   ├── ApiError.java
│   ├── FormaPagamentoNaoSuportadaException.java
│   ├── GlobalExceptionHandler.java
│   └── PedidoInvalidoException.java
│
├── facade
│   └── PagamentoFacade.java
│
├── factory
│   └── PagamentoStrategyFactory.java
│
├── service
│   ├── ComprovanteService.java
│   ├── NotificacaoService.java
│   └── PedidoService.java
│
└── strategy
    ├── PagamentoStrategy.java
    ├── PagamentoPixStrategy.java
    ├── PagamentoCreditoStrategy.java
    └── PagamentoDebitoStrategy.java
```

# Design Patterns utilizados

## 1. Strategy

O padrão Strategy é usado para representar diferentes algoritmos de pagamento.

A interface principal é:

```java
public interface PagamentoStrategy {

    PagamentoResponse pagar(PagamentoRequest request);

    FormaPagamento getFormaPagamento();
}
```

Cada forma de pagamento possui sua própria implementação.

```text
PagamentoStrategy
       |
       +-- PagamentoPixStrategy
       |
       +-- PagamentoCreditoStrategy
       |
       +-- PagamentoDebitoStrategy
```

Dessa forma, cada tipo de pagamento possui seu próprio comportamento.

Uma nova forma de pagamento pode ser adicionada criando uma nova implementação da interface.

Exemplo:

```java
@Component
public class PagamentoBoletoStrategy implements PagamentoStrategy {
}
```

Isso ajuda a aplicar o princípio **Open/Closed (OCP)** do SOLID.

---

## 2. Factory

O `PagamentoStrategyFactory` é responsável por selecionar a Strategy correta.

Exemplo:

```java
PagamentoStrategy strategy =
        strategyFactory.obter(request.formaPagamento());
```

O restante da aplicação não precisa saber qual classe concreta deverá ser criada ou utilizada.

A Factory recebe automaticamente todas as implementações de `PagamentoStrategy` cadastradas pelo Spring.

---

## 3. Facade

O `PagamentoFacade` concentra todo o fluxo necessário para processar um pagamento.

O fluxo é:

```text
Controller
    |
    v
PagamentoFacade
    |
    +-- valida pedido
    |
    +-- seleciona Strategy
    |
    +-- processa pagamento
    |
    +-- atualiza pedido
    |
    +-- gera comprovante
    |
    +-- envia notificação
```

Sem a Facade, o Controller precisaria conhecer e chamar vários serviços.

Com a Facade, o Controller faz apenas:

```java
return pagamentoFacade.processar(request);
```

Isso reduz o acoplamento entre a camada HTTP e as regras internas da aplicação.

---

## 4. Singleton

Por padrão, os componentes gerenciados pelo Spring utilizam escopo Singleton.

Por exemplo:

```java
@Service
public class PedidoService {
}
```

Durante a execução da aplicação, o Spring cria uma única instância desse Bean e reutiliza essa instância sempre que necessário.

O mesmo acontece com:

```java
@Service
@Component
@RestController
```

quando nenhum outro escopo é configurado.

Assim, o conceito do Singleton aparece no projeto através do próprio gerenciamento de Beans do Spring.

---

# Fluxo completo

```text
POST /pagamentos
       |
       v
PagamentoController
       |
       v
PagamentoFacade
       |
       +-------------------------------+
       |                               |
       v                               v
PedidoService              PagamentoStrategyFactory
                                       |
                                       v
                              PagamentoStrategy
                                       |
                         +-------------+-------------+
                         |             |             |
                         v             v             v
                        PIX         CREDITO        DEBITO
                         |
                         v
                PagamentoResponse
                         |
                         +--> atualizar pedido
                         |
                         +--> gerar comprovante
                         |
                         +--> enviar notificação
```

# Executando

É necessário ter:

- Java 21
- Maven

Execute:

```bash
mvn spring-boot:run
```

A aplicação será executada em:

```text
http://localhost:8080
```

# Endpoint

## Criar pagamento

```http
POST /pagamentos
```

### PIX

```json
{
  "pedidoId": 123,
  "valor": 150.00,
  "formaPagamento": "PIX"
}
```

### Crédito

```json
{
  "pedidoId": 124,
  "valor": 299.90,
  "formaPagamento": "CREDITO"
}
```

### Débito

```json
{
  "pedidoId": 125,
  "valor": 75.50,
  "formaPagamento": "DEBITO"
}
```

Exemplo de resposta:

```json
{
  "pedidoId": 123,
  "valor": 150.00,
  "formaPagamento": "PIX",
  "status": "APROVADO",
  "mensagem": "Pagamento via PIX aprovado com sucesso.",
  "processadoEm": "2026-08-27T18:00:00"
}
```

# Testando com cURL

```bash
curl -X POST http://localhost:8080/pagamentos \
-H "Content-Type: application/json" \
-d '{
    "pedidoId": 123,
    "valor": 150.00,
    "formaPagamento": "PIX"
}'
```

# Testes automatizados

Execute:

```bash
mvn test
```

O projeto possui testes básicos para:

- seleção de Strategy pela Factory;
- processamento do pagamento pela Facade.

# Relação com SOLID

Além dos Design Patterns, a implementação aplica conceitos do SOLID.

### SRP — Single Responsibility Principle

Cada classe possui uma responsabilidade específica.

Exemplo:

```text
PedidoService          -> pedidos
NotificacaoService     -> notificações
ComprovanteService     -> comprovantes
PagamentoStrategy      -> processamento do pagamento
```

### OCP — Open/Closed Principle

Novas formas de pagamento podem ser adicionadas sem alterar as Strategies existentes.

### DIP — Dependency Inversion Principle

A aplicação trabalha com a abstração:

```java
PagamentoStrategy
```

em vez de depender diretamente apenas de classes concretas.

# Possíveis evoluções

O projeto pode ser evoluído futuramente com:

- PostgreSQL
- Spring Data JPA
- Docker
- Swagger / OpenAPI
- autenticação JWT
- histórico de pagamentos
- status de pedidos
- integração simulada com gateways
- Kafka ou RabbitMQ para notificações
- padrões Observer e Chain of Responsibility

# Objetivo acadêmico

Este projeto foi desenvolvido como exercício prático para consolidar conhecimentos de Design Patterns em Java, demonstrando como esses padrões podem ser utilizados para criar código mais organizado, desacoplado, extensível e fácil de manter.
