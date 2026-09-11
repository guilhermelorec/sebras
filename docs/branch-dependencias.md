# Branch `dependencias`

## Identificação

| Campo | Valor |
|---|---|
| Branch | `dependencias` |
| Base do PR | `main` |
| PR | [#1](https://github.com/guilhermelorec/sebras/pull/1) (mesclado) |
| Commits | `99c81d5` dependências/Oracle/WildFly; `7a0722b` merge do README do `main` |

## Resumo

Deixou o Sebras compilável com Java 21 e executável com Docker (Oracle Free + WildFly 41). Incluiu Maven, JARs, schema SQL, JNDI `java:global/jdbc/VotacaoUCP`, servlet de municípios e correção do SQL de resultado.

## Como rodar

```bash
mvn -DskipTests package
docker compose up --build -d
```

Abrir http://localhost:8080/sebras/

- Banco: `localhost:1521/FREEPDB1`
- Usuário/senha: `votacao` / `votacao`
- JNDI: `java:global/jdbc/VotacaoUCP`

Parar: `docker compose down`  
Restaurar JARs: `mvn generate-resources`

## Bibliotecas e runtime

| Artefato | Versão | Uso |
|---|---|---|
| `jakarta.servlet-api` | 6.1.0 | Servlets (`lib/`, provided no WAR) |
| `jakarta.servlet.jsp-api` | 4.0.0 | JSP (`lib/`, provided no WAR) |
| `ojdbc11` | 23.9.0.25.07 | Driver Oracle (`lib/`, 7.2M) |
| `ucp` | 23.9.0.25.07 | Pool Oracle (`lib/`, 1.5M) |
| maven-compiler-plugin | 3.13.0 | `release` 21 |
| maven-war-plugin | 3.4.0 | WAR `sebras.war` |
| maven-dependency-plugin | 3.8.1 | Copia JARs para `lib/` |
| `gvenzl/oracle-free` | 23-slim-faststart | Container Oracle (1521) |
| `quay.io/wildfly/wildfly` | latest (41.0.1) | Container WildFly (8080) |
| OpenJDK | 21 | Compilação (já na máquina) |
| Maven | 3.8.7 | Build (já na máquina) |

## Pastas criadas

```
lib/                  JARs Eclipse
docker/oracle/        Dockerfile do banco + schema no init
docker/wildfly/       Dockerfile, module.xml, configure.cli
```

## Arquivos criados

| Arquivo | Linhas | Função |
|---|---|---|
| `pom.xml` | 1–98 | Maven WAR, Java 21, dependências |
| `.gitignore` | 1 | ignora `target/` |
| `.dockerignore` | 1–13 | contexto Docker |
| `docker-compose.yml` | 1–39 | Oracle + WildFly |
| `docker/oracle/Dockerfile` | 1–3 | copia `schema.sql` |
| `docker/wildfly/Dockerfile` | 1–16 | módulo ojdbc + deploy do WAR |
| `docker/wildfly/module.xml` | 1–11 | módulo `com.oracle.ojdbc` |
| `docker/wildfly/configure.cli` | 1–4 | driver + DS JNDI |
| `resource/schema.sql` | 1–114 | CREATE TABLE do domínio |
| `src/servlets/MunicipioServlet.java` | 1–72 | CRUD `/municipios` |
| `web/jsp/municipios.jsp` | 1–95 | tela de municípios (versão sem CSS) |
| `web/WEB-INF/web.xml` | 1–10 | welcome-file `index.jsp` |
| `README.md` | 1 | vindo do `main` no merge `7a0722b` |
| `lib/*.jar` | binários | quatro JARs listados acima |

## Arquivos alterados (linhas)

**`.classpath`**
- L3: JRE `jdk-26` → `JavaSE-21`
- L8–28: user libraries `jakarta`/`jdbc` → JARs em `lib/`

**`.settings/org.eclipse.jdt.core.prefs`**
- L2: `targetPlatform` 26 → 21
- L4: `compliance` 26 → 21
- L13: `source` 26 → 21

**`src/appVoto/ApplicationContext.java`**
- L50–54: se JNDI falhar, usa `UCPConnectionFactory`

**`src/dao/jdbc/VotoJdbcDAO.java`**
- L63: `ca.numero` → `c.numero`
- removido `JOIN cargo ca` (cargo não tem `numero`)
- L74: `GROUP BY c.numero, e.nome, p.sigla`

## Arquivos movidos

`web/*.jsp` → `web/jsp/*.jsp` (conteúdo igual; servlets já faziam forward para `/jsp/...`):

`abstencao`, `candidaturas`, `cargos`, `eleicoes`, `eleitores`, `partidos`, `resultado`, `secoes`, `votar`, `zonas`

`web/index.jsp` permaneceu na raiz de `web/`.

## Estatística (99c81d5 vs 17d460f)

30 arquivos, +501 / −12 linhas (sem contar o merge do README).
