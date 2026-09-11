# Branch `frontend`

## Identificação

| Campo | Valor |
|---|---|
| Branch | `frontend` |
| Base do PR | `main` (já contém o merge da `dependencias`) |
| Commit | `2c623f4` — Adiciona interface visual compartilhada para o SEBRAS. |
| Compare | https://github.com/guilhermelorec/sebras/compare/main...frontend |

## Resumo

As telas eram HTML sem estilo. Esta branch adiciona CSS próprio, layout compartilhado (topbar + rodapé), home em cards e aplica o visual em todas as JSPs. Servlets, URLs e nomes de campos do formulário **não** mudaram.

## Como ver

http://localhost:8080/sebras/

Se o WildFly já estava no ar:

```bash
mvn -DskipTests package
docker compose up --build -d wildfly
```

Atualizar o navegador com Ctrl+F5.

## Bibliotecas

Nenhuma dependência Java nova (`pom.xml` e `lib/` iguais).

Somente frontend:

- CSS local: `/sebras/css/sebras.css`
- Google Fonts: **Fraunces** (títulos) e **Source Sans 3** (texto)

## Pastas criadas

```
web/css/              folha de estilo
web/jsp/includes/     layout-start.jsp e layout-end.jsp
docs/                 documentação por branch (esta pasta)
.cursor/rules/        regra para gerar estes docs em todo PR
```

## Arquivos criados

| Arquivo | Linhas | Função |
|---|---|---|
| `web/css/sebras.css` | 1–354 | topbar, cards, forms, tabelas, badges, mobile |
| `web/jsp/includes/layout-start.jsp` | 1–58 | head, fontes, CSS, logo, navegação, abre `<main>` |
| `web/jsp/includes/layout-end.jsp` | 1–10 | fecha layout e rodapé |

## Arquivos alterados (linhas)

**`web/WEB-INF/web.xml`**
- L10–15: `jsp-config` com `page-encoding` UTF-8 (acentos nas JSPs)

**`web/index.jsp`** (1–99)
- L2–5: include do layout (`title=Início`, `current=home`)
- L7–17: hero
- L19–97: 11 cards de navegação

**Telas em `web/jsp/`** — passaram a usar `layout-start` / `layout-end`, `form-grid`, tabelas e badges:

| Arquivo | Diff | Conteúdo |
|---|---|---|
| `partidos.jsp` | +/− ~152 | cadastro de partidos |
| `municipios.jsp` | +/− ~136 | cadastro de municípios |
| `zonas.jsp` | +/− ~139 | cadastro de zonas |
| `secoes.jsp` | +/− ~155 | cadastro de seções |
| `eleitores.jsp` | +/− ~176 | cadastro de eleitores |
| `eleicoes.jsp` | +/− ~201 | cadastro de eleições |
| `cargos.jsp` | +/− ~155 | cadastro de cargos |
| `candidaturas.jsp` | +/− ~255 | cadastro de candidaturas |
| `votar.jsp` | +/− ~93 | urna |
| `resultado.jsp` | +/− ~126 | apuração por zona |
| `abstencao.jsp` | +/− ~130 | abstenção por zona |

Detalhe do `layout-start.jsp`:
- L3–11: lê `title` e `current`
- L19–22: fontes + `sebras.css`
- L25–55: topbar e links
- L56–57: abre `main.page` e `wrap`

## O que não mudou

`src/`, `pom.xml`, `lib/`, `docker/`, `resource/schema.sql`, rotas (`/partidos`, `/votar`, etc.) e nomes dos inputs POST/GET.

## Estatística (`2c623f4`)

16 arquivos, +1316 / −946 linhas.
