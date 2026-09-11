---
tags:
  - processo
  - modelo
  - ers
fonte: 99-fontes/originais/modelo_especificacao_requisitos_software.doc
---

# Especificação de requisitos de software (ERS)

Fonte: `modelo_especificacao_requisitos_software.doc`.  
Instância Sebras: [[Catalogo-de-requisitos]], [[Visao-do-produto]], [[RNF01]].

## 1. Introdução

### 1.1 Propósito
Definir o que o Sebras deve fazer, para quem, e com quais restrições. Serve de contrato entre cliente e implementação.

### 1.2 Escopo
Ver [[Escopo-atual]] e [[Visao-do-produto]].

### 1.3 Definições
Ver [[Glossario]].

### 1.4 Referências
- Modelos da pasta `99-fontes/originais`
- Schema `resource/schema.sql`
- Código em `src/` (situação atual, não desejada futura)

## 2. Descrição geral

### 2.1 Perspectiva do produto
Aplicação web Jakarta Servlet + JSP, Oracle, WildFly. Uso local/acadêmico de urna digital: território → cadastros → voto → apuração.

### 2.2 Funções do produto
Cadastro de partido, eleitor, município, zona, seção, eleição, cargo e candidatura; registro de voto; resultado e abstenção por zona.

### 2.3 Características dos usuários
Mesa eleitoral / operador acadêmico. Sem autenticação no `main` atual.

### 2.4 Restrições
Oracle, Java 21, WAR no WildFly, sem frontend obrigatório no `main`.

## 3. Requisitos específicos

Cada RF/RNF é uma nota em `03-requisitos`. Não escrever requisito só nesta página.

## Relacionado

- [[Detalhamento-caso-de-uso]]
- [[Classificacao-priorizacao]]
- [[Matriz-rastreabilidade]]
