---
tags:
  - processo
  - modelo
  - scm
fonte: 99-fontes/originais/modelo_formulario_solicitacao_mudancas.doc
---

# Formulário de solicitação de mudanças

Fonte: `modelo_formulario_solicitacao_mudancas.doc`.  
Registro vivo: [[Registro-de-mudancas]]. Template: `_templates/solicitacao-mudanca.md`.

## Campos obrigatórios

1. Identificador (SCM-NNN)
2. Data e solicitante
3. Tipo: corretiva / evolutiva / preventiva / adaptativa
4. Requisito afetado (RC / RF / RNF / CU / RN)
5. Descrição da situação atual
6. Descrição da mudança proposta
7. Justificativa
8. Impacto (código, banco, telas, testes, prazo)
9. Prioridade e esforço
10. Decisão: aprovada / rejeitada / adiada
11. Aprovador e data

Nenhuma mudança entra no `main` sem SCM se ela alterar regra de negócio, schema ou RF já documentado.
