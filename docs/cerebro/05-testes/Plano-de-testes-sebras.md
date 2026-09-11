---
tags:
  - testes
  - sebras
---

# Plano de testes — Sebras

Forma: [[Plano-de-testes]]. Cabeçalho: [[Padrao-documental]].  
Ainda **não executado** como campanha formal. Isto é o norte.

## 1. Introdução

### 1.1 Propósito
Identificar o que testar no Sebras, o ambiente, os critérios e as entregas.

### 1.2 Escopo
Aplicação em http://localhost:8080/sebras/ com Oracle + WildFly via Docker.

#### 1.2.1 Etapas
Teste de sistema / funcional, ponto de vista do operador da mesa. Manual na iteração atual.

#### 1.2.2 Funcionalidades a testar
[[RF01]] … [[RF11]] e regras [[RN01]] … [[RN07]] no voto.

#### 1.2.3 O que não será testado agora
- Visual da branch `frontend` ([[RNF08]])
- Autenticação ([[RNF07]])
- Cluster, Solaris, FTP e Sistema de Categorias do plano-exemplo
- LOG de atividades (existia no plano de Limite de Crédito; Sebras não tem esse RF)

### 1.3 Referências
[[ERS]] · [[Catalogo-de-requisitos]] · [[Regras-de-negocio]] · `resource/schema.sql`

## 2. Requisitos dos testes

Todos os RF do [[Catalogo-de-requisitos]]. Prioridade em [[Classificacao-priorizacao-sebras]].

## 3. Abordagem

Mesmo quadro do modelo: dado válido → resultado esperado; dado inválido → mensagem; cada RN aplicada.

### Prontidão
- `docker compose` com Oracle healthy e WildFly no ar
- Schema `votacao` criado
- WAR gerado de `main` (`mvn clean -DskipTests package`)
- Casos de uso lidos

### Completeza
Todos os CT abaixo executados; defeitos anotados em [[Registro-de-mudancas]] se mudarem regra.

### Suspensão / retomada
| # | Suspende | Retoma |
|---|---|---|
| 1 | WildFly fora | container `sebras-wildfly` up |
| 2 | Oracle off-line | `sebras-oracle` healthy |
| 3 | Schema ausente | recriar volume / rodar schema |

## 4. Ambiente

| Recurso | Sebras atual |
|---|---|
| App | WildFly, porta 8080, contexto `/sebras` |
| Banco | Oracle Free 23, `1521/FREEPDB1`, user `votacao` |
| Cliente | Browser qualquer (sem login) |

## 5. Casos de teste iniciais

| ID | CU / RF | Passo curto | Esperado |
|---|---|---|---|
| CT-RF01-01 | [[CU01]] | Cadastrar partido 13 / PT | Linha na lista |
| CT-RF03-01 | [[CU03]] | Cadastrar município | Linha na lista |
| CT-RF04-01 | [[CU04]] | Zona no município | FK ok |
| CT-RF05-01 | [[CU05]] | Seção na zona | FK ok |
| CT-RF02-01 | [[CU02]] | Eleitor na zona/seção | Título único |
| CT-RF02-02 | [[CU02]] | Mesmo título de novo | Recusa / erro |
| CT-RF06-01 | [[CU06]] | Eleição ativa turno 1 | Lista |
| CT-RF07-01 | [[CU07]] | Cargo com ou sem UF | Lista |
| CT-RF08-01 | [[CU08]] | Candidatura ativa | Lista |
| CT-RF09-01 | [[CU09]] | Voto válido | Comparecimento + voto |
| CT-RF09-02 | [[CU09]] | Segundo voto mesmo turno | [[RN06]] |
| CT-RF09-03 | [[CU09]] | Eleição inativa | [[RN01]] |
| CT-RF09-04 | [[CU09]] | Turno ≠ atual | [[RN02]] |
| CT-RF09-05 | [[CU09]] | Seção de outra zona | [[RN03]] |
| CT-RF09-06 | [[CU09]] | Cargo de outra UF | [[RN05]] |
| CT-RF10-01 | [[CU10]] | Resultado da zona após voto | Total ≥ 1 |
| CT-RF11-01 | [[CU11]] | Abstenção da zona | Eleitor que não votou |

## 6. Entregas futuras

Plano revisado, evidências (prints/HTTP), relatório final, lições aprendidas — quando formos executar de fato.
