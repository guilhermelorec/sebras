# Branch `cerebro`

## Identificação

| Campo | Valor |
|---|---|
| Branch | `cerebro` |
| Base do PR | `main` (`ba3cd2f`) |
| Commit | (hash no `git log -1` desta branch) — Cria o vault Obsidian de regras de negócio. |

## Resumo

O projeto não tinha um norte formal de requisitos. Esta branch adiciona o vault `docs/cerebro/` (ERS, RC/RF/RNF, casos de uso, regras, matriz, plano de testes, SCM) e a regra Cursor que obriga o código a seguir esse cérebro. **Não altera Java, JSP, schema em `resource/` nem Docker.**

## Como usar / validar

No Obsidian: **Open folder as vault** em `docs/cerebro` (ou a cópia em `Documentos\Sebras\sebras`). Abrir `Cerebro.md` → `Home`.

No Cursor a regra `.cursor/rules/regra-negocio-cerebro.mdc` vale em toda sessão.

## Pastas e arquivos

| Ação | Caminho |
|---|---|
| Criado | `docs/cerebro/` (vault: processo, negócio, requisitos, CUs, testes, SCM, atas, fontes) |
| Criado | `docs/cerebro/02-negocio/schema-sebras.sql` (DDL das 10 tabelas do ER) |
| Criado | `docs/cerebro/99-fontes/originais/` (modelos .doc/.xls/.docx) |
| Criado | `.cursor/rules/regra-negocio-cerebro.mdc` |
| Criado | `docs/branch-cerebro.md` |
| Alterado | `docs/README.md` (índice + link do vault) |

## Linhas relevantes

- `docs/cerebro/02-negocio/Entidades.md` L28–L41: as 10 tabelas (sem `candidato`).
- `docs/cerebro/02-negocio/Regras-de-negocio.md`: RN01–RN10 (voto).
- `docs/cerebro/03-requisitos/Catalogo-de-requisitos.md`: RC01–07, RF01–11, RNF01–08.
- `docs/cerebro/06-mudancas/SCM-001.md`: alinhamento do schema ao ER.
- `.cursor/rules/regra-negocio-cerebro.mdc` L1–L27: `alwaysApply: true`.

## Bibliotecas

Nenhuma. Sem JAR, imagem Docker ou plugin novo.

## O que não mudou

`resource/schema.sql`, servlets, DAOs, WildFly/Oracle e a branch `banco-de-dados` (lá está a remoção de `candidato` no código).
