<%@ page import="java.util.List" %>
<%@ page import="votacao.AbstencaoZona" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Abstenção"/>
    <jsp:param name="current" value="abstencao"/>
</jsp:include>

<h1 class="page-title">Abstenção por zona</h1>
<p class="page-lead">Compare eleitores aptos, presentes e ausentes.</p>

<%
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");
%>

<section class="panel">
    <h2>Consulta</h2>
    <form method="get" action="abstencao" class="form-grid">
        <label class="field">Eleição
            <select name="eleicaoId">
                <%
                    if (eleicoes != null) {
                        for (Eleicao eleicao : eleicoes) {
                %>
                <option value="<%= eleicao.getId() %>"><%= eleicao.getNome() %> - <%= eleicao.getAno() %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Turno
            <select name="turno">
                <option value="1">1º turno</option>
                <option value="2">2º turno</option>
            </select>
        </label>
        <div class="actions">
            <button type="submit">Consultar</button>
        </div>
    </form>
</section>

<section class="table-wrap">
    <h2>Indicadores</h2>
    <div class="table-scroll">
        <table>
            <tr>
                <th>Zona</th>
                <th>Município</th>
                <th>Eleitores</th>
                <th>Presentes</th>
                <th>Abstenção</th>
                <th>Taxa</th>
            </tr>
            <%
                List<AbstencaoZona> abstencao = (List<AbstencaoZona>) request.getAttribute("abstencao");
                if (abstencao != null) {
                    for (AbstencaoZona item : abstencao) {
            %>
            <tr>
                <td><%= item.getNumeroZona() %></td>
                <td><%= item.getMunicipio() %></td>
                <td><%= item.getTotalEleitores() %></td>
                <td><%= item.getPresentes() %></td>
                <td><%= item.getAbstencao() %></td>
                <td><%= String.format("%.2f", item.getTaxaAbstencao()) %>%</td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (abstencao == null || abstencao.isEmpty()) { %>
        <p class="empty">Nenhum dado de abstenção para os filtros informados.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
