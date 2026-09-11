<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Zonas"/>
    <jsp:param name="current" value="zonas"/>
</jsp:include>

<h1 class="page-title">Zonas eleitorais</h1>
<p class="page-lead">Vincule cada zona a um município.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Zona> zonas = (List<Zona>) request.getAttribute("zonas");
    List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="zonas" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Número
            <input type="number" name="numero" required/>
        </label>
        <label class="field">Município
            <select name="municipioId" required>
                <%
                    if (municipios != null) {
                        for (Municipio municipio : municipios) {
                %>
                <option value="<%= municipio.getId() %>"><%= municipio.getNome() %> - <%= municipio.getUf() %></option>
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
                <th>Número</th>
                <th>Município</th>
                <th>Ações</th>
            </tr>
            <%
                if (zonas != null) {
                    for (Zona zona : zonas) {
            %>
            <tr>
                <td><%= zona.getId() %></td>
                <td><%= zona.getNumero() %></td>
                <td><%= zona.getNomeMunicipio() %></td>
                <td>
                    <form class="inline-form" method="post" action="zonas">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= zona.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (zonas == null || zonas.isEmpty()) { %>
        <p class="empty">Nenhuma zona cadastrada.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
