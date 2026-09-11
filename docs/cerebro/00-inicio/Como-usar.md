---
tags:
  - processo
  - sebras
---

# Como usar este cérebro

Regra: **nenhuma alteração de código começa sem um RF/CU/SCM apontado aqui.**

## Fluxo para um RF novo

1. Abrir ata em [[07-atas/Indice-atas]] (se houver reunião).
2. Registrar o desejo do cliente como `RC` em `03-requisitos/RC`.
3. Quebrar em `RF` / `RNF` usando [[ERS]].
4. Classificar benefício, esforço, risco, estabilidade e qualidade em [[Classificacao-priorizacao-sebras]].
5. Detalhar caso de uso com [[Detalhamento-caso-de-uso]].
6. Ligar RF ↔ RC ↔ RNF ↔ CU ↔ código em [[Matriz-rastreabilidade-sebras]].
7. Se já existir implementação, abrir [[Solicitacao-de-mudanca]] e anotar impacto.
8. Só então implementar. Validar com [[Plano-de-testes-sebras]].
9. Avaliar qualidade do requisito com [[Laudo-avaliacao-requisitos]] quando o RF for formalizado.

## Convenções de ID

| Prefixo | Significado | Exemplo |
|---|---|---|
| RC | Requisito de cliente | [[RC01]] |
| RF | Requisito funcional | [[RF01]] |
| RNF | Requisito não funcional | [[RNF01]] |
| RN | Regra de negócio | [[RN01]] |
| CU | Caso de uso | [[CU01]] |
| CT | Caso de teste | CT-RF09-01 |
| SCM | Solicitação de mudança | SCM-001 |
| PT | Produto de trabalho | PT-ERS, PT-CU01 |

## Status permitido

`proposto` · `aprovado` · `em-implementacao` · `implementado` · `adiado` · `cancelado`

## O que este cérebro não é

- Não substitui `docs/branch-*.md` (isso é histórico de commit/PR).
- Não é o código. O código segue o cérebro, não o contrário.
- Não inventa RF que o cliente não pediu. Se o código já faz, documentamos como **implementado**.
