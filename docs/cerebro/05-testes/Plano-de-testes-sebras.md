---
tags:
  - testes
  - sebras
---

# Plano de testes — Sebras

Forma: [[Plano-de-testes]] (9 seções do modelo). Cabeçalho: [[Padrao-documental]].  
Manutenção e regressão: [[Plano-de-manutencao-sebras]].

## Controle de revisões

| Versão | Descrição | Data | Autor | Revisor |
|---|---|---|---|---|
| 0.1 | Norte inicial, só funcional | 2026-09-11 | Equipe Sebras | Pendente |
| 0.2 | 9 seções do modelo, tipos de teste, casos negativos, achados do dump | 2026-10-15 | Equipe Sebras | Pendente |

## 1. Introdução

### 1.1 Propósito
Identificar o que testar no Sebras, como, com quem, em que ambiente, com quais critérios e quais entregas.

### 1.2 Escopo
Aplicação em http://localhost:8080/sebras/ com Oracle + WildFly via Docker, código do `main`.

#### 1.2.1 Etapas
Unitário → integração → sistema/funcional → concorrência → segurança → aceitação.

#### 1.2.2 Funcionalidades a testar
[[RF01]] … [[RF11]] e regras [[RN01]] … [[RN10]].

#### 1.2.3 O que não será testado agora
- Visual da branch `frontend` ([[RNF08]])
- Autenticação / perfis ([[RNF07]]) — não existe; os testes de segurança registram a ausência como risco
- Desempenho em escala nacional (volume real do TSE)
- Cluster, Solaris, FTP e Sistema de Categorias do plano-exemplo
- LOG de atividades (existia no plano de Limite de Crédito; Sebras não tem esse RF)

### 1.3 Referências
[[ERS]] · [[Catalogo-de-requisitos]] · [[Regras-de-negocio]] · [[Matriz-rastreabilidade-sebras]] · `resource/schema.sql` · `src/service/VotacaoService.java` · dump `db-sebrax-20-09-2026.sql` (modelo PHP/PostgreSQL do professor, usado como fonte de dados ruins)

## 2. Requisitos dos testes

Todos os RF do [[Catalogo-de-requisitos]]. Prioridade em [[Classificacao-priorizacao-sebras]].

### 2.1 Rastreabilidade RF/RN → CT

| RF / RN | CT válido | CT inválido |
|---|---|---|
| [[RF01]] | CT-RF01-01 | CT-RF01-02, CT-RF01-03 |
| [[RF02]] | CT-RF02-01 | CT-RF02-02, CT-RF02-03, CT-RF02-04 |
| [[RF03]] | CT-RF03-01 | CT-RF03-02 |
| [[RF04]] | CT-RF04-01 | CT-RF04-02 |
| [[RF05]] | CT-RF05-01 | CT-RF05-02 |
| [[RF06]] | CT-RF06-01 | CT-RF06-02 |
| [[RF07]] | CT-RF07-01 | CT-RF07-02 |
| [[RF08]] | CT-RF08-01 | CT-RF08-02, CT-RF08-03 |
| [[RF09]] | CT-RF09-01 | CT-RF09-02 … CT-RF09-10 |
| [[RF10]] | CT-RF10-01 | CT-RF10-02 |
| [[RF11]] | CT-RF11-01 | CT-RF11-02 |
| [[RN01]] | CT-RF09-01 | CT-RF09-03 |
| [[RN02]] | CT-RF09-01, CT-RF09-11 | CT-RF09-04, CT-RF09-05 |
| [[RN03]] | CT-RF09-01 | CT-RF09-06 |
| [[RN04]] | CT-RF09-01 | CT-RF09-07, CT-RF09-08, CT-CON-01 |
| [[RN05]] | CT-RF09-01 | CT-RF09-09, CT-RF09-10 |
| [[RN06]] | CT-RF09-01 | CT-RF09-02, CT-CON-01 |
| [[RN07]] | CT-INT-03 | CT-INT-04 |
| [[RN08]] | CT-RF02-01 | CT-RF02-02 |
| [[RN09]] | CT-RF09-01 | CT-RF09-07, CT-RF09-09 |
| [[RN10]] | CT-RF10-01 | CT-RF10-02 |

A mesma coluna CT entra na [[Matriz-rastreabilidade-sebras]].

## 3. Abordagem

Regra geral: dado válido → resultado esperado; dado inválido → mensagem de erro e **nada gravado**. Cada RN tem pelo menos um CT válido e um inválido (seção 2.1).

### 3.1 Tipos de teste

| Tipo | Alvo | Como | Manual / automático |
|---|---|---|---|
| Unitário | Validações do `VotacaoService` ([[RN01]]–[[RN07]]) | JUnit 5 com `ConnectionFactory` apontando para Oracle de teste; um teste por mensagem de `RegraNegocioException` | Automático |
| Integração | DAO ↔ banco: FKs, uniques, rollback da [[RN07]] | JUnit + Oracle no Docker, schema recriado a cada rodada | Automático |
| Sistema / funcional | Cada CU pela tela | Browser, roteiro da seção 5 | Manual |
| Concorrência | Dois votos simultâneos do mesmo eleitor | Duas threads JUnit ou duas abas/`curl` em paralelo | Automático |
| Segurança | SQL injection, XSS, sigilo do voto, CSRF | Payloads nos formulários + consulta no banco | Manual |
| Aceitação | Fluxo completo da mesa | Ordem do [[Escopo-atual]]: município → … → resultado/abstenção, executado com o cliente/professor | Manual |

### 3.2 Prontidão (critério de início)
- `docker compose up` com `sebras-oracle` healthy e `sebras-wildfly` no ar
- Schema `votacao` criado a partir de `resource/schema.sql`
- WAR gerado de `main` (`mvn clean -DskipTests package`)
- Massa de dados da seção 6.0 carregada
- Plano e CT revisados

### 3.3 Completeza (critério de fim)
- Todos os CT executados, com evidência
- Nenhum defeito **crítico** aberto (voto, resultado, sigilo)
- Defeitos restantes registrados e, se mudarem regra, com SCM em [[Registro-de-mudancas]]

### 3.4 Suspensão / retomada

| # | Suspende | Retoma |
|---|---|---|
| 1 | WildFly fora | container `sebras-wildfly` up |
| 2 | Oracle off-line | `sebras-oracle` healthy |
| 3 | Schema ausente ou sujo | recriar volume / rodar schema |
| 4 | Defeito crítico no voto (CT-RF09-01 falha) | correção no `main` e reexecução da regressão mínima |

### 3.5 Ferramentas

| Uso | Ferramenta |
|---|---|
| Unitário / integração / concorrência | JUnit 5 + Maven (`mvn test`) |
| Funcional / segurança | Browser + DevTools, `curl` |
| Banco | SQL Developer ou `sqlplus` no container |
| Ambiente | Docker Compose |
| Defeitos e evidências | Este vault (`05-testes/`) + GitHub Issues |

## 4. Recursos

### 4.1 Humanos

| Papel | Responsabilidade | Nome |
|---|---|---|
| Gerente de testes | Plano, cronograma, critérios, relatório final |  |
| Testador funcional | CT-RF01 … CT-RF11, aceitação |  |
| Testador técnico | Unitário, integração, concorrência |  |
| Testador de segurança | CT-SEG |  |
| DBA | Schema, massa de dados, consultas de conferência |  |
| Cliente / professor | Aceitação |  |

### 4.2 Ambiente

| Recurso | Sebras atual |
|---|---|
| App | WildFly, porta 8080, contexto `/sebras` |
| Banco | Oracle Free 23, `1521/FREEPDB1`, user `votacao` |
| Cliente | Browser qualquer (sem login) |
| Máquina | PC x86-64 com Docker |

## 5. Treinamento

| Quem | O quê | Duração |
|---|---|---|
| Toda a equipe | Ler [[Regras-de-negocio]], [[Escopo-atual]] e os CU | 2 h |
| Testadores | Subir o ambiente Docker e recriar o schema | 1 h |
| Testador técnico | JUnit 5 e transação JDBC (`commit`/`rollback`, `FOR UPDATE`) | 2 h |
| Testador de segurança | OWASP: SQL injection, XSS, CSRF | 2 h |

## 6. Casos de teste

### 6.0 Massa de dados base

Município *Goiânia/GO* · zona 1 · seções 1 e 2 na zona 1 · zona 2 com seção 3 · partidos 13/PT e 22/PL · eleitor **A** (zona 1, seção 1, ativo) · eleitor **B** (zona 1, seção 1, inativo) · eleição 2026 ativa, turno 1, 2º turno habilitado · cargo Presidente (sem UF, permite 2º turno) · cargo Senador (UF `DF`, não permite 2º turno) · candidatura Presidente ativa · candidatura Senador DF ativa · candidatura inativa.

### 6.1 Funcionais — cadastros

| ID | CU / RF | Passo curto | Esperado |
|---|---|---|---|
| CT-RF01-01 | [[CU01]] | Cadastrar partido 13 / PT | Linha na lista |
| CT-RF01-02 | [[CU01]] | Partido sem sigla ou sem nome | Recusa, nada gravado |
| CT-RF01-03 | [[CU01]] | Segundo partido com número 13 | Recusa (**hoje aceita**: sem unique) |
| CT-RF02-01 | [[CU02]] | Eleitor na zona/seção, título novo | Linha na lista |
| CT-RF02-02 | [[CU02]] | Mesmo título de novo | Recusa ([[RN08]]) |
| CT-RF02-03 | [[CU02]] | CPF `0` / `123456`, título `7` | Recusa por formato (**hoje aceita**) |
| CT-RF02-04 | [[CU02]] | Seção que não pertence à zona escolhida | Recusa (**hoje aceita**: sem check) |
| CT-RF03-01 | [[CU03]] | Cadastrar município | Linha na lista |
| CT-RF03-02 | [[CU03]] | UF `XX` ou vazia | Recusa |
| CT-RF04-01 | [[CU04]] | Zona no município | FK ok |
| CT-RF04-02 | [[CU04]] | Zona com município inexistente | Recusa (FK) |
| CT-RF05-01 | [[CU05]] | Seção na zona | FK ok |
| CT-RF05-02 | [[CU05]] | Seção com zona inexistente | Recusa (FK) |
| CT-RF06-01 | [[CU06]] | Eleição ativa turno 1 | Lista |
| CT-RF06-02 | [[CU06]] | Turno atual `3` ou data do 2º turno antes do 1º | Recusa |
| CT-RF07-01 | [[CU07]] | Cargo com ou sem UF | Lista |
| CT-RF07-02 | [[CU07]] | Cargo `Imperador` | Recusa (**hoje aceita**: nome livre) |
| CT-RF08-01 | [[CU08]] | Candidatura ativa | Lista |
| CT-RF08-02 | [[CU08]] | Dois candidatos com o mesmo número, cargo e eleição | Recusa (**hoje aceita**) |
| CT-RF08-03 | [[CU08]] | Candidato do PL (22) a Presidente com número 99 | Recusa: prefixo ≠ número do partido (**hoje aceita**) |

### 6.2 Funcionais — voto, resultado, abstenção

| ID | CU / RN | Passo curto | Esperado |
|---|---|---|---|
| CT-RF09-01 | [[CU09]] | Eleitor A vota em Presidente, turno 1 | 1 comparecimento + 1 voto |
| CT-RF09-02 | [[RN06]] | Eleitor A vota de novo no turno 1 | *Eleitor já votou nesta eleição/turno.* |
| CT-RF09-03 | [[RN01]] | Votar com eleição `ativa = 0` | *Eleição inativa.* |
| CT-RF09-04 | [[RN02]] | Turno `3` | *Turno inválido.* |
| CT-RF09-05 | [[RN02]] | Turno 2 com `turno_atual = 1` | *O turno informado não é o turno atual da eleição.* |
| CT-RF09-06 | [[RN03]] | Seção 3 com zona 1 | *Seção não pertence à zona informada.* |
| CT-RF09-07 | [[RN04]] [[RN09]] | Eleitor B (inativo) | *Eleitor não encontrado ou inativo…* |
| CT-RF09-08 | [[RN04]] | Eleitor A informando seção 2 | *Eleitor não encontrado ou inativo…* |
| CT-RF09-09 | [[RN05]] [[RN09]] | Candidatura inativa | *Candidatura inválida…* |
| CT-RF09-10 | [[RN05]] | Eleitor de GO vota em Senador DF | *Candidatura inválida…* |
| CT-RF09-11 | [[RN02]] [[RN05]] | Turno 2 habilitado: Presidente ok, Senador recusado | 1 voto gravado, 1 recusado |
| CT-RF10-01 | [[CU10]] | Resultado da zona após voto | Total ≥ 1 |
| CT-RF10-02 | [[RN10]] | Resultado de zona sem votos / inexistente | Lista vazia, sem erro 500 |
| CT-RF11-01 | [[CU11]] | Abstenção da zona | Eleitor que não votou aparece |
| CT-RF11-02 | [[CU11]] | Após o voto de A | A não aparece na abstenção |

### 6.3 Unitários — `VotacaoService`

| ID | Regra | Entrada | Esperado |
|---|---|---|---|
| CT-UNI-01 | [[RN01]] | eleição inexistente | `RegraNegocioException` *Eleição não encontrada.* |
| CT-UNI-02 | [[RN01]] | `ativa = 0` | *Eleição inativa.* |
| CT-UNI-03 | [[RN02]] | turno 0 / 3 | *Turno inválido.* |
| CT-UNI-04 | [[RN02]] | turno 2, `segundo_turno_habilitado = 0` | *Segundo turno não está habilitado.* |
| CT-UNI-05 | [[RN03]] | seção fora da zona | *Seção não pertence à zona informada.* |
| CT-UNI-06 | [[RN04]] | eleitor inativo / outra seção | *Eleitor não encontrado ou inativo…* |
| CT-UNI-07 | [[RN05]] | cargo de outra UF / inativa / turno 2 sem permissão | *Candidatura inválida…* |
| CT-UNI-08 | [[RN06]] | comparecimento já existe | *Eleitor já votou…* |
| CT-UNI-09 | todas | dados válidos | sem exceção, `commit` |

### 6.4 Integração — DAO ↔ banco

| ID | Alvo | Passo | Esperado |
|---|---|---|---|
| CT-INT-01 | FKs | Inserir zona/seção/eleitor/voto com FK inexistente | `SQLException`, nada gravado |
| CT-INT-02 | Uniques | `eleitor.titulo` repetido; `comparecimento` repetido | Violação de constraint |
| CT-INT-03 | [[RN07]] | Voto válido | `count(comparecimento)` e `count(voto)` sobem 1 cada |
| CT-INT-04 | [[RN07]] | Forçar falha no insert do voto (ex.: `candidatura_id` inexistente) | Rollback: **nem** comparecimento **nem** voto gravados |
| CT-INT-05 | Consistência | Após toda a campanha | `count(voto) = count(comparecimento)` por eleição/turno |

### 6.5 Concorrência

| ID | Passo | Esperado |
|---|---|---|
| CT-CON-01 | Duas threads votam ao mesmo tempo com o eleitor A, mesma eleição/turno | Exatamente 1 sucesso, 1 *já votou*; 1 comparecimento, 1 voto (`FOR UPDATE` da [[RN04]] + unique da [[RN06]]) |
| CT-CON-02 | 50 eleitores diferentes votam em paralelo | 50 votos, 50 comparecimentos, nenhum erro de deadlock |

### 6.6 Segurança

| ID | Passo | Esperado |
|---|---|---|
| CT-SEG-01 | Nome do partido/eleitor `' OR '1'='1` e `'; DROP TABLE voto; --` | Gravado como texto literal; tabelas intactas (DAOs usam `PreparedStatement`) |
| CT-SEG-02 | Nome do partido `<script>alert(1)</script>`, depois abrir `/partidos` | Texto aparece escapado. **Risco conhecido**: JSPs usam `<%= ... %>` sem escape → deve falhar hoje |
| CT-SEG-03 | Repetir CT-SEG-02 em eleitor, município, cargo, candidatura | Mesmo esperado |
| CT-SEG-04 | Sigilo: após CT-RF09-01, consultar `voto` e `comparecimento` | Nenhuma coluna liga eleitor a candidatura. No `main` passa (voto não tem `eleitor_id`). No modelo do dump **falha**: `voto (eleitor_id, candidato_id)` |
| CT-SEG-05 | Sigilo por ordem: cruzar `comparecimento.id` com `voto.id` | Ids não podem revelar quem votou em quem (**risco**: ambos são sequenciais e inseridos juntos) |
| CT-SEG-06 | POST para `/votar` vindo de outra origem (página HTML externa) | Recusa (**hoje aceita**: sem token CSRF, sem login — [[RNF07]]) |

### 6.7 Aceitação

| ID | Passo | Esperado |
|---|---|---|
| CT-ACE-01 | Banco vazio → município → zona → seção → partido → eleitor → eleição → cargo → candidatura → voto → resultado → abstenção, pela tela | Fluxo completo sem erro; resultado e abstenção batem com os votos dados |

### 6.8 Achados do dump `db-sebrax-20-09-2026.sql`

O dump é do modelo PHP/PostgreSQL, mas mostra dados que o banco **aceitou** e não deveria. Cada um vira CT no Sebras:

| Dado no dump | Problema | CT |
|---|---|---|
| CPF `'0'`, `'123456'`; título `'7'` | Sem validação de formato | CT-RF02-03 |
| Eleitores com `zona_id NULL` e `secao_id = 5` (seção da zona 6) | Zona/seção inconsistentes | CT-RF02-04 (no `main`, `zona_id` é NOT NULL, mas não há check seção × zona) |
| Candidatura 7: partido PL (22), número 99 | Número não bate com o partido | CT-RF08-03 |
| Cargo `'Imperador'` | Cargo fora de Presidente/Senador/Deputado Federal | CT-RF07-02 |
| `voto (eleitor_id, candidato_id)` | Quebra o sigilo do voto | CT-SEG-04 + SCM |

## 7. Programação

### 7.1 Cronograma

| Semana | Atividade | Prioridade |
|---|---|---|
| 1 | Revisar plano, montar ambiente e massa de dados, treinamento | 5 |
| 2 | Unitário + integração (CT-UNI, CT-INT) | 5 |
| 2 | Funcional do voto (CT-RF09) e concorrência (CT-CON) | 5 |
| 3 | Funcional dos cadastros (CT-RF01 … CT-RF08), resultado e abstenção (CT-RF10, CT-RF11) | 4 |
| 3 | Segurança (CT-SEG) | 4 |
| 4 | Correções + regressão, aceitação (CT-ACE), relatório final | 5 |

### 7.2 Milestones

| Marco | Critério |
|---|---|
| M1 — Ambiente pronto | Prontidão da seção 3.2 cumprida |
| M2 — Voto validado | CT-RF09, CT-UNI, CT-INT, CT-CON executados |
| M3 — Campanha completa | Todos os CT executados, defeitos registrados |
| M4 — Aceite | CT-ACE aprovado pelo cliente/professor; relatório final entregue |

## 8. Riscos e contingências

| Risco | Impacto | Contingência |
|---|---|---|
| Ambiente Docker/Oracle não sobe | Bloqueia tudo | Imagem e volume documentados; recriar volume; máquina reserva |
| Sem projeto de teste JUnit no repositório | Unitário/integração atrasam | Começar pelo funcional manual; criar `src/test` na semana 1 |
| XSS confirmado (CT-SEG-02) | Segurança | Abrir SCM corretiva: escapar saída nos JSP (`fn:escapeXml` / `c:out`) |
| Sem CSRF/login | Segurança | Registrar como risco aceito ([[RNF07]]); SCM evolutiva |
| Validações ausentes (CPF, cargo, número) | Dados ruins | SCMs corretivas/preventivas do [[Plano-de-manutencao-sebras]] |
| Equipe pequena / prazo da disciplina | Cobertura | Seguir a prioridade 5 primeiro (voto) |

## 9. Entregas

| Entrega | Conteúdo |
|---|---|
| Plano de testes | Este documento, versão aprovada |
| Casos de teste | Seção 6, com resultado (passou / falhou) por CT |
| Evidências | Prints da tela, saída do `mvn test`, consultas SQL antes/depois |
| Relatório de defeitos | ID, CT, passos, esperado × obtido, severidade, SCM aberta |
| Relatório final | CT executados/passaram/falharam, defeitos por severidade, riscos restantes, lições aprendidas |

## Relacionado

- [[Plano-de-testes]]
- [[Plano-de-manutencao-sebras]]
- [[Matriz-rastreabilidade-sebras]]
- [[Registro-de-mudancas]]
