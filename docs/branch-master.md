# Branch `master`

## Identificação

| Campo | Valor |
|---|---|
| Branch | `master` |
| Commit | `17d460f` — initial commit |
| Remoto | `origin/master` |
| Base | histórico próprio (não era o `main` do GitHub) |

## Resumo

Primeiro envio do código Sebras: aplicação Jakarta Servlet + JSP + JDBC Oracle, sem Maven, sem CSS e sem schema SQL versionado. O `main` do GitHub só tinha `README.md` (`3a955b8`).

## Pastas principais (estado inicial)

```
src/           código Java (appVoto, dao, servlets, service, votacao, tankDB)
web/           JSPs na raiz (index + telas)
bin/           classes compiladas pelo Eclipse
resource/      requisitos (PDF, XLSX, TXT, PNG)
sebras/        pasta vazia
```

## Bibliotecas (Eclipse, não versionadas neste commit)

- User library `jakarta` (Servlet)
- User library `jdbc` (Oracle)
- JRE configurado como `jdk-26` no `.classpath`

## Como estava para rodar

Não havia `pom.xml` nem Docker. Dependia de Eclipse + WildFly + Oracle externos.
