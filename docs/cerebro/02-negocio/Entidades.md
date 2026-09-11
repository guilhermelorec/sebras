---
tags:
  - negocio
  - dados
---

# Entidades e persistência

Fonte de verdade do banco: `resource/schema.sql` (usuário `votacao`).  
O mesmo DDL está em [[schema-sebras]].

```mermaid
erDiagram
  MUNICIPIO ||--o{ ZONA : contem
  ZONA ||--o{ SECAO : contem
  ZONA ||--o{ ELEITOR : aloca
  SECAO ||--o{ ELEITOR : aloca
  PARTIDO ||--o{ CANDIDATURA : lanca
  ELEITOR ||--o{ CANDIDATURA : e
  ELEICAO ||--o{ CANDIDATURA : disputa
  CARGO ||--o{ CANDIDATURA : para
  CANDIDATURA ||--o{ VOTO : recebe
  ELEITOR ||--o{ COMPARECIMENTO : registra
  ELEICAO ||--o{ COMPARECIMENTO : no
  ELEICAO ||--o{ VOTO : no
```

## Tabelas

| Tabela | PK | Observação |
|---|---|---|
| partido | id IDENTITY | numero, sigla, nome, ativo |
| municipio | id IDENTITY | nome, uf |
| zona | id IDENTITY | numero, municipio_id |
| secao | id IDENTITY | numero, zona_id, local |
| eleitor | id IDENTITY | titulo UNIQUE, zona+seção, ativo, votou |
| eleicao | id IDENTITY | ano, datas, turno_atual, 2º turno, ativa |
| cargo | id IDENTITY | nome, uf opcional, permite_segundo_turno |
| candidatura | id IDENTITY | eleitor+partido+eleicao+cargo+numero, ativa |
| voto | id IDENTITY | candidatura+eleicao+turno+zona+seção |
| comparecimento | id IDENTITY | UNIQUE (eleitor, eleicao, turno) |

## Relacionado

- [[Regras-de-negocio]]
- [[Escopo-atual]]
