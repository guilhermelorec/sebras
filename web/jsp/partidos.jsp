<%@ page import="votacao.Partido" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Partidos"/>
    <jsp:param name="current" value="partidos"/>
</jsp:include>

<h1 class="page-title">Partidos</h1>
<p class="page-lead">Cadastre legendas com número, sigla e nome.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Partido> partidos = (List<Partido>) request.getAttribute("partidos");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="partidos" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Número
            <input type="number" name="numero" required/>
        </label>
        <label class="field">Sigla
            <input type="text" name="sigla" required/>
        </label>
        <label class="field">Nome
            <input type="text" name="nome" required/>
        </label>
        <label class="field field-check">
            <input type="checkbox" name="ativo" checked/> Ativo
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
                <th>Número</th>
                <th>Sigla</th>
                <th>Nome</th>
                <th>Ativo</th>
                <th>Ações</th>
            </tr>
            <%
                if (partidos != null) {
                    for (Partido partido : partidos) {
            %>
            <tr>
                <td><%= partido.getId() %></td>
                <td><%= partido.getNumero() %></td>
                <td><%= partido.getSigla() %></td>
                <td><%= partido.getNome() %></td>
                <td><span class="badge <%= partido.isAtivo() ? "badge-ok" : "badge-off" %>"><%= partido.isAtivo() ? "Sim" : "Não" %></span></td>
                <td>
                    <form class="inline-form" method="post" action="partidos">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= partido.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (partidos == null || partidos.isEmpty()) { %>
        <p class="empty">Nenhum partido cadastrado.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
