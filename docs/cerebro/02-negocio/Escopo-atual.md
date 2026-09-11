---
tags:
  - negocio
  - escopo
---

# Escopo atual versus desejado

## Dentro do `main` (já no código)

| Tela / rota | RF | CU |
|---|---|---|
| `/partidos` | [[RF01]] | [[CU01]] |
| `/eleitores` | [[RF02]] | [[CU02]] |
| `/municipios` | [[RF03]] | [[CU03]] |
| `/zonas` | [[RF04]] | [[CU04]] |
| `/secoes` | [[RF05]] | [[CU05]] |
| `/eleicoes` | [[RF06]] | [[CU06]] |
| `/cargos` | [[RF07]] | [[CU07]] |
| `/candidaturas` | [[RF08]] | [[CU08]] |
| `/votar` | [[RF09]] | [[CU09]] |
| `/resultado` | [[RF10]] | [[CU10]] |
| `/abstencao` | [[RF11]] | [[CU11]] |

## Fora do `main` (não misturar)

| Item | Onde está | Status no cérebro |
|---|---|---|
| Visual civic (CSS + includes) | branch `frontend` | Adiado até RF de UI |
| Autenticação / perfis | inexistente | Sem RC ainda |
| TSE / urna oficial | fora | Fora de escopo acadêmico |
| Tabela `candidato` / seed `/zonas/seed` | removidos | Fora do ER e dos RF/CU |

## Ordem operacional sugerida (mesa)

Município → zona → seção → partido → eleitor → eleição → cargo → candidatura → voto → resultado / abstenção.

## Relacionado

- [[Visao-do-produto]]
- [[Catalogo-de-requisitos]]
