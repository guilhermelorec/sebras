---
tags:
  - negocio
  - regras
---

# Regras de negócio

Código-fonte atual: `src/service/VotacaoService.java`.  
Estas regras **já valem**. Mudar qualquer uma exige [[Solicitacao-de-mudanca]].

## [[RN01]] Eleição precisa estar ativa

Se `eleicao.ativa != 1`, o voto é recusado: *Eleição inativa.*

## [[RN02]] Turno só 1 ou 2, e igual ao turno atual

Turno informado deve ser `1` ou `2` e coincidir com `eleicao.turno_atual`.  
Segundo turno só se `segundo_turno_habilitado = 1`.

## [[RN03]] Seção pertence à zona

`secao.zona_id` deve ser a zona informada no voto.

## [[RN04]] Eleitor ativo na zona/seção informadas

Eleitor precisa existir, `ativo = 1`, e estar cadastrado exatamente na zona e seção do voto. O registro é bloqueado (`FOR UPDATE`) durante o voto.

## [[RN05]] Candidatura válida para o eleitor

A candidatura precisa estar `ativa`, ser da mesma eleição, e:
- o cargo sem UF ou com UF igual à do município da zona do eleitor;
- no 2º turno, o cargo precisa `permite_segundo_turno = 1`.

## [[RN06]] Um voto por eleitor / eleição / turno

Se já existe `comparecimento` para o trio, recusa: *Eleitor já votou nesta eleição/turno.*  
O comparecimento é gravado na mesma transação do voto.

## [[RN07]] Voto e comparecimento são atômicos

Insert de comparecimento + insert de voto no mesmo commit. Qualquer falha faz rollback.

## [[RN08]] Título de eleitor é único

Constraint `uk_eleitor_titulo`.

## [[RN09]] Partido, eleitor, candidatura e eleição têm flag de ativo/ativa

Cadastros “removem” por flag ou exclusão da tela; o voto só aceita eleitor ativo e candidatura ativa.

## [[RN10]] Resultado e abstenção são por zona

Não há boletim nacional no `main`. Consultas: [[RF10]], [[RF11]].

## Relacionado

- [[CU09]]
- [[RF09]]
- [[Voto]]
