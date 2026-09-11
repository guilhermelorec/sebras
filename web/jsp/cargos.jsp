<%@ page import="java.util.List" %>
<%@ page import="votacao.Cargo" %>
<%@ page import="votacao.UnidadeFederativa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Cargos"/>
    <jsp:param name="current" value="cargos"/>
</jsp:include>

<h1 class="page-title">Cargos</h1>
<p class="page-lead">Cargos nacionais ou restritos a uma UF.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="cargos" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Nome
            <input type="text" name="nome" required/>
        </label>
        <label class="field">UF
            <select name="uf">
                <option value="">Nacional</option>
                <%
                    for (UnidadeFederativa uf : UnidadeFederativa.values()) {
                %>
                <option value="<%= uf.name() %>"><%= uf.name() %> - <%= uf.getNome() %></option>
                <%
                    }
                %>
            </select>
        </label>
        <label class="field field-check">
            <input type="checkbox" name="permiteSegundoTurno"/> Permite segundo turno
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
                <th>UF</th>
                <th>Segundo turno</th>
                <th>Ações</th>
            </tr>
            <%
                if (cargos != null) {
                    for (Cargo cargo : cargos) {
            %>
            <tr>
                <td><%= cargo.getId() %></td>
                <td><%= cargo.getNome() %></td>
                <td><%= cargo.getUf() == null ? "Nacional" : cargo.getUf() %></td>
                <td><span class="badge <%= cargo.isPermiteSegundoTurno() ? "badge-ok" : "badge-off" %>"><%= cargo.isPermiteSegundoTurno() ? "Sim" : "Não" %></span></td>
                <td>
                    <form class="inline-form" method="post" action="cargos">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= cargo.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (cargos == null || cargos.isEmpty()) { %>
        <p class="empty">Nenhum cargo cadastrado.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
