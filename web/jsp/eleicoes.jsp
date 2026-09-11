<%@ page import="java.util.List" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Eleições"/>
    <jsp:param name="current" value="eleicoes"/>
</jsp:include>

<h1 class="page-title">Eleições</h1>
<p class="page-lead">Configure o pleito, as datas e o turno atual.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="eleicoes" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Nome
            <input type="text" name="nome" required/>
        </label>
        <label class="field">Ano
            <input type="number" name="ano" required/>
        </label>
        <label class="field">Data turno 1
            <input type="date" name="dataTurno1" required/>
        </label>
        <label class="field">Data turno 2
            <input type="date" name="dataTurno2"/>
        </label>
        <label class="field">Turno atual
            <select name="turnoAtual">
                <option value="1">1º turno</option>
                <option value="2">2º turno</option>
            </select>
        </label>
        <label class="field field-check">
            <input type="checkbox" name="segundoTurnoHabilitado"/> Segundo turno habilitado
        </label>
        <label class="field field-check">
            <input type="checkbox" name="ativa" checked/> Ativa
        </label>
        <div class="actions">
            <button type="submit">Salvar</button>
        </div>
    </form>
</section>

<section class="table-wrap">
    <h2>Lista</h2>
    <div class="table-scroll">
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Ano</th>
                <th>Turno 1</th>
                <th>Turno 2</th>
                <th>Turno atual</th>
                <th>Segundo turno</th>
                <th>Ativa</th>
                <th>Ações</th>
            </tr>
            <%
                if (eleicoes != null) {
                    for (Eleicao eleicao : eleicoes) {
            %>
            <tr>
                <td><%= eleicao.getId() %></td>
                <td><%= eleicao.getNome() %></td>
                <td><%= eleicao.getAno() %></td>
                <td><%= eleicao.getDataTurno1() %></td>
                <td><%= eleicao.getDataTurno2() %></td>
                <td><%= eleicao.getTurnoAtual() %></td>
                <td><span class="badge <%= eleicao.isSegundoTurnoHabilitado() ? "badge-ok" : "badge-off" %>"><%= eleicao.isSegundoTurnoHabilitado() ? "Sim" : "Não" %></span></td>
                <td><span class="badge <%= eleicao.isAtiva() ? "badge-ok" : "badge-off" %>"><%= eleicao.isAtiva() ? "Sim" : "Não" %></span></td>
                <td>
                    <form class="inline-form" method="post" action="eleicoes">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= eleicao.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (eleicoes == null || eleicoes.isEmpty()) { %>
        <p class="empty">Nenhuma eleição cadastrada.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
