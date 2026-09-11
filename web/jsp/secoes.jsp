<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Seções"/>
    <jsp:param name="current" value="secoes"/>
</jsp:include>

<h1 class="page-title">Seções eleitorais</h1>
<p class="page-lead">Defina o número e o local de cada seção.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Secao> secoes = (List<Secao>) request.getAttribute("secoes");
    List<Zona> zonas = (List<Zona>) request.getAttribute("zonas");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="secoes" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Número
            <input type="number" name="numero" required/>
        </label>
        <label class="field">Zona
            <select name="zonaId" required>
                <%
                    if (zonas != null) {
                        for (Zona zona : zonas) {
                %>
                <option value="<%= zona.getId() %>">Zona <%= zona.getNumero() %> - <%= zona.getNomeMunicipio() %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Local
            <input type="text" name="local"/>
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
                <th>Zona</th>
                <th>Local</th>
                <th>Ações</th>
            </tr>
            <%
                if (secoes != null) {
                    for (Secao secao : secoes) {
            %>
            <tr>
                <td><%= secao.getId() %></td>
                <td><%= secao.getNumero() %></td>
                <td><%= secao.getNumeroZona() %></td>
                <td><%= secao.getLocal() %></td>
                <td>
                    <form class="inline-form" method="post" action="secoes">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= secao.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (secoes == null || secoes.isEmpty()) { %>
        <p class="empty">Nenhuma seção cadastrada.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
