# Branch `banco-de-dados`

## Identificação

| Campo | Valor |
|---|---|
| Branch | `banco-de-dados` |
| Base do PR | `main` (`ba3cd2f`) |
| Commits | `e853e2d` remove `candidato` e seed de zonas; merge de `origin/main` para resolver `docs/README.md`. |

## Resumo

O schema tinha a tabela `candidato` (legado, sem RF/CU) e o código tinha SQL de seed em `/zonas/seed`. O modelo do cérebro lista só 10 tabelas. Esta branch alinha o DDL e apaga o Java que não pertence a esse modelo.

## Como rodar / validar

```bash
mvn -DskipTests package
```

No Oracle (`votacao` / `FREEPDB1`):

```sql
DROP TABLE candidato CASCADE CONSTRAINTS;
SELECT table_name FROM user_tables ORDER BY 1;
```

Devem restar: `CANDIDATURA`, `CARGO`, `COMPARECIMENTO`, `ELEICAO`, `ELEITOR`, `MUNICIPIO`, `PARTIDO`, `SECAO`, `VOTO`, `ZONA`.

Schema novo em container vazio: `docker compose down` **sem** `-v` mantém dados; para recriar o schema do zero, `docker compose down -v` e `up --build`.

## Pastas e arquivos

| Ação | Caminho |
|---|---|
| Alterado | `resource/schema.sql` |
| Alterado | `src/appVoto/ApplicationContext.java` |
| Removido | `src/dao/CandidatoDAO.java` |
| Removido | `src/dao/jdbc/CandidatoJdbcDAO.java` |
| Removido | `src/votacao/Candidato.java` |
| Removido | `src/service/ZonaService.java` |
| Removido | `src/servlets/ZonaSeedServlet.java` |
| Criado | `docs/branch-banco-de-dados.md` |
| Alterado | `docs/README.md` (índice desta branch) |

## Linhas relevantes

- `resource/schema.sql` L4–L5: comentário das 10 tabelas do modelo.
- `resource/schema.sql`: bloco `CREATE TABLE candidato` (antes L47–L55) removido. `eleitor` termina em L48; `eleicao` começa em L50.
- `ApplicationContext.java`: removidos `import ZonaService` e `zonaService()` (antes L29 e L97–L99).

## Bibliotecas

Nenhuma biblioteca, JAR, imagem Docker ou plugin novo. `ojdbc11` / `ucp` 23.9.0.25.07 e Oracle Free no compose seguem iguais.

## O que não mudou

Servlets e DAOs de partido, eleitor, município, zona, seção, eleição, cargo, candidatura, voto, resultado e abstenção. JSPs e frontend.

## Merge com `main` (PR #4 `cerebro`)

O `main` ganhou o vault em `docs/cerebro/` (PR #4). As duas branches editavam `docs/README.md`. O índice ficou com as duas linhas: `cerebro` mesclado e `banco-de-dados` pendente.
