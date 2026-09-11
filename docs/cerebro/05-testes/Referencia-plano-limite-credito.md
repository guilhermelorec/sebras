---
tags:
  - testes
  - referencia
  - nao-sebras
fonte: 99-fontes/originais/plano_testes_limite_credito.docx
---

# Referência — Plano de testes Limite de Crédito

**Isto não é regra do Sebras.** É o `.docx` que veio junto, já preenchido para outro sistema (operadora *Empresas Generosas*, atribuição de limite de crédito a assinantes). Serve só para ver como o molde é usado.

## Sistema do exemplo

Automatizar limite de crédito: obter arquivo de assinantes por categoria, configurar parâmetros e JOB, cadastrar/autenticar usuários, gerar e enviar arquivo de limite.

## REQF do exemplo (não implementar no Sebras)

| ID | Texto |
|---|---|
| REQF001 | Solução web |
| REQF002 | Três browsers |
| REQF003 | Telas de autenticação, parâmetros, JOBs, usuários |
| REQF007 | Autenticação primeiro |
| REQF008 | Dois tipos de acesso |
| REQF009 | Admin pré-cadastrado |
| REQF012 | Arquivo de limite por região |
| REQF014–015 | LOG de ações e erros |
| REQF017–018 | Layout dos arquivos |
| REQF019 | Uma ou mais configs de coleta/envio |

Não testar no exemplo: LOG de atividades e de erros.

## Ambiente do exemplo

Solaris 9, Oracle 9, cluster `APPSERV_LIMCRED_01/02`, IE6 / Firefox 3, Mantis 1.2.4, cronograma abril–maio/2011.

## O que aproveitar

A divisão em prontidão / completeza / suspensão, a tabela de prioridade 1–5, milestones e a lista “o que testa / o que não testa”. Já transposta em [[Plano-de-testes-sebras]].
