<%@ page import="votacao.Eleitor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Eleitores"/>
    <jsp:param name="current" value="eleitores"/>
</jsp:include>

<h1 class="page-title">Eleitores</h1>
<p class="page-lead">Cadastre o eleitor com título, zona e seção.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Eleitor> eleitores = (List<Eleitor>) request.getAttribute("eleitores");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="eleitores" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Título
            <input type="text" name="titulo" required/>
        </label>
        <label class="field">CPF
            <input type="text" name="cpf"/>
        </label>
        <label class="field">Nome
            <input type="text" name="nome" required/>
        </label>
        <label class="field">Zona ID
            <input type="number" name="zonaId" required/>
        </label>
        <label class="field">Seção ID
            <input type="number" name="secaoId" required/>
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
                <th>Título</th>
                <th>CPF</th>
                <th>Nome</th>
                <th>Zona</th>
                <th>Seção</th>
                <th>Ativo</th>
                <th>Votou</th>
                <th>Ações</th>
            </tr>
            <%
                if (eleitores != null) {
                    for (Eleitor eleitor : eleitores) {
            %>
            <tr>
                <td><%= eleitor.getId() %></td>
                <td><%= eleitor.getTitulo() %></td>
                <td><%= eleitor.getCpf() %></td>
                <td><%= eleitor.getNome() %></td>
                <td><%= eleitor.getZonaId() %></td>
                <td><%= eleitor.getSecaoId() %></td>
                <td><span class="badge <%= eleitor.isAtivo() ? "badge-ok" : "badge-off" %>"><%= eleitor.isAtivo() ? "Sim" : "Não" %></span></td>
                <td><span class="badge <%= eleitor.isVotou() ? "badge-ok" : "badge-off" %>"><%= eleitor.isVotou() ? "Sim" : "Não" %></span></td>
                <td>
                    <form class="inline-form" method="post" action="eleitores">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= eleitor.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (eleitores == null || eleitores.isEmpty()) { %>
        <p class="empty">Nenhum eleitor cadastrado.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
