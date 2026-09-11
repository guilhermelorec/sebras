<%@ page import="java.util.List" %>
<%@ page import="votacao.Municipio" %>
<%@ page import="votacao.UnidadeFederativa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Municípios"/>
    <jsp:param name="current" value="municipios"/>
</jsp:include>

<h1 class="page-title">Municípios</h1>
<p class="page-lead">Base territorial usada por zonas e seções.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    UnidadeFederativa[] ufs = (UnidadeFederativa[]) request.getAttribute("ufs");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="municipios" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Nome
            <input type="text" name="nome" required/>
        </label>
        <label class="field">UF
            <select name="uf" required>
                <%
                    if (ufs != null) {
                        for (UnidadeFederativa uf : ufs) {
                %>
                <option value="<%= uf.name() %>"><%= uf.name() %> - <%= uf.getNome() %></option>
                <%
                        }
                    }
                %>
            </select>
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
                <th>Ações</th>
            </tr>
            <%
                if (municipios != null) {
                    for (Municipio municipio : municipios) {
            %>
            <tr>
                <td><%= municipio.getId() %></td>
                <td><%= municipio.getNome() %></td>
                <td><%= municipio.getUf() %></td>
                <td>
                    <form class="inline-form" method="post" action="municipios">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= municipio.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (municipios == null || municipios.isEmpty()) { %>
        <p class="empty">Nenhum município cadastrado.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
