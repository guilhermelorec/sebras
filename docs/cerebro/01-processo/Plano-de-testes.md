---
tags:
  - processo
  - modelo
  - testes
fonte: 99-fontes/originais/plano_testes_limite_credito.docx
---

# Plano de testes (modelo de forma)

Fonte: o `.docx` `64ba3c87-…` — **Plano de Testes** preenchido para o sistema *Limite de Crédito / Empresas Generosas*, não para o Sebras.

Usamos a **estrutura**. O plano do Sebras é [[Plano-de-testes-sebras]]. O texto original está em [[Referencia-plano-limite-credito]].

## Sumário que o modelo exige

1. Introdução — propósito, escopo, o que testa / o que não testa, referências
2. Requisitos dos testes (IDs REQF / no Sebras: RF)
3. Abordagem — tipos, prontidão, completeza, suspensão/retomada, ferramentas
4. Recursos humanos e de ambiente
5. Treinamento
6. Programação — cronograma, prioridade 1..5, abordagem manual/automática
7. Milestones
8. Riscos e contingências
9. Entregas

## Tipos de teste do modelo

- **Teste funcional / de sistema**: executar cada caso de uso com dados válidos e inválidos; mensagens de erro; regras de negócio.
- Critério de início: versão disponível, plano e casos aprovados, ambiente no ar.
- Critério de fim: todos os CT executados; defeitos registrados.

## Ferramentas citadas no modelo (referência)

Excel, Word, Mantis 1.2.4. No Sebras o equivalente atual é este vault + HTTP manual + Docker.

## Relacionado

- [[Plano-de-testes-sebras]]
- [[Referencia-plano-limite-credito]]
