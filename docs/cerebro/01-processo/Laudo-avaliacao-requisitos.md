---
tags:
  - processo
  - modelo
fonte: 99-fontes/originais/modelo_laudo_avaliacao_requisitos.doc
---

# Laudo de avaliação de requisitos

Fonte: `modelo_laudo_avaliacao_requisitos.doc`.

Usar quando um RF for formalizado ou quando um SCM alterar requisito.

## Critérios do laudo

| Critério | Pergunta |
|---|---|
| Completude | Falta ator, dado, fluxo ou restrição? |
| Clareza | Dá para implementar sem adivinhar? |
| Consistência | Conflita com outro RF/RN? |
| Rastreabilidade | Tem RC, CU e (se já existir) código? |
| Testabilidade | Dá para escrever CT com resultado esperado? |
| Viabilidade | Cabe no Oracle + Servlet/JSP atuais? |
| Prioridade | Classificado em [[Classificacao-priorizacao-sebras]]? |

## Resultado

`aprovado` · `aprovado-com-ressalvas` · `reprovado`

Guardar o laudo como nota em `03-requisitos` ligada ao RF, ou em `06-mudancas` se nasceu de SCM.
