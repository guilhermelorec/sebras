---
tags:
  - processo
  - modelo
fonte: 99-fontes/originais/modelo_documento_class_prior.xls
---

# Classificação e priorização de requisitos

Fonte: `modelo_documento_class_prior.xls` (aba *Requisitos Funcionais*).

A planilha original traz **exemplo de outro domínio** (cadastro de alunos / instituições). Não usar esses RF como regra do Sebras. A instância válida é [[Classificacao-priorizacao-sebras]].

## Colunas do modelo

| Campo | Valores do modelo |
|---|---|
| Código | RF01… |
| Descrição | Texto do requisito |
| Fornecedor | Quem pediu |
| Status | Proposto / Aprovado |
| Benefício | Útil / Importante / Crítico |
| Esforço | Baixo / Médio / Alto |
| Risco | Baixo / Médio / Alto |
| Estabilidade | Baixa / Média / Alta |
| Qualidade | Ruim / Regular / Bom |
| Prioridade | 0…5 (0 = mais urgente no exemplo da planilha) |
| Pontuação | Soma ponderada dos atributos convertidos |

## Conversão numérica do modelo

| Atributo | 1 | 2 | 3 |
|---|---|---|---|
| Benefício | Útil | Importante | Crítico |
| Esforço | Baixo | Médio | Alto |
| Risco | Baixo | Médio | Alto |
| Estabilidade | Baixa | Média | Alta |
| Qualidade | Ruim | Regular | Bom |

Pesos no exemplo da planilha: todos `1` na conversão simples; há bloco de pesos `3` à direita para pontuação alternativa.

## Exemplo que veio no XLS (não é Sebras)

| Código | Descrição | Fornecedor | Benefício | Esforço | Prioridade | Pontuação |
|---|---|---|---|---|---|---|
| RF01 | Manter cadastro de alunos | SILVIO | Útil | Baixo | 2 | 5 |
| RF02 | Manter controle de instituições beneficentes | SILVIO | Importante | Médio | 1 | 10 |
| RF03 | Manter controle dos cursos ministrados | REGINA | Crítico | Alto | 0 | 15 |

## Relacionado

- [[Classificacao-priorizacao-sebras]]
- [[Catalogo-de-requisitos]]
