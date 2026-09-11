<%@ page import="java.util.List" %>
<%@ page import="votacao.AbstencaoZona" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Abstenção por Zona</title>
</head>
<body>

<h1>Abstenção por Zona Eleitoral</h1>

<%
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");
%>

<form method="get" action="abstencao">

    <label>Eleição:
        <select name="eleicaoId">
            <%
                if (eleicoes != null) {
                    for (Eleicao eleicao : eleicoes) {
            %>
            <option value="<%= eleicao.getId() %>">
                <%= eleicao.getNome() %> - <%= eleicao.getAno() %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <label>Turno:
        <select name="turno">
            <option value="1">1º Turno</option>
            <option value="2">2º Turno</option>
        </select>
    </label>
    <br/>

    <button type="submit">Consultar</button>

</form>

<br/>

<table border="1" cellpadding="4">
    <tr>
        <th>Zona</th>
        <th>Município</th>
        <th>Total de Eleitores</th>
        <th>Presentes</th>
        <th>Abstenção</th>
        <th>Taxa (%)</th>
    </tr>

    <%
        List<AbstencaoZona> abstencao =
                (List<AbstencaoZona>) request.getAttribute("abstencao");

        if (abstencao != null) {
            for (AbstencaoZona item : abstencao) {
    %>
    <tr>
        <td><%= item.getNumeroZona() %></td>
        <td><%= item.getMunicipio() %></td>
        <td><%= item.getTotalEleitores() %></td>
        <td><%= item.getPresentes() %></td>
        <td><%= item.getAbstencao() %></td>
        <td><%= String.format("%.2f", item.getTaxaAbstencao()) %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<br/>
<a href="${pageContext.request.contextPath}/">Voltar</a>

</body>
</html>
