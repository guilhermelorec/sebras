---
tags:
  - negocio
  - sebras
  - visao
---

# Visão do produto

**Sebras** — Sistema Eleitoral Brasileiro, parte 2: votação e apuração acadêmica.

## Problema

Registrar o território eleitoral, os atores (partido, eleitor, candidatura) e o voto, depois apurar resultado e abstenção por zona, com regras que impedem voto inválido ou duplicado.

## Solução

Aplicação web em Jakarta Servlet + JSP, persistência Oracle, deploy WildFly. Operação pela mesa eleitoral digital, sem urna física.

## Fronteira atual (`main`)

Implementado: cadastros + voto + resultado + abstenção.  
Fora do `main`: visual da branch `frontend`, autenticação, TSE real, biometria, urna offline.

## Conceitos do domínio

Território: [[Municipio]] → [[Zona]] → [[Secao]].  
Atores: [[Partido]], [[Eleitor]], [[Candidatura]] (eleitor + partido + cargo + eleição).  
Pleito: [[Eleicao]], [[Cargo]], [[Voto]], [[Comparecimento]].  
Consultas: [[Resultado-por-zona]], [[Abstencao-por-zona]].

## Relacionado

- [[Glossario]]
- [[Regras-de-negocio]]
- [[Escopo-atual]]
- [[Entidades]]
