<%@ page import="java.util.List" %>
<%@ page import="votacao.ResultadoVotacao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resultado por Zona</title>
</head>
<body>

<h1>Resultado por Zona Eleitoral</h1>

<form method="get" action="resultado">

    <label>Zona ID:
        <input type="number"
               name="zonaId"
               value="<%= request.getParameter("zonaId") == null ? "" : request.getParameter("zonaId") %>"
               required/>
    </label>
    <br/>

    <label>Eleição ID:
        <input type="number"
               name="eleicaoId"
               value="<%= request.getParameter("eleicaoId") == null ? "" : request.getParameter("eleicaoId") %>"
               required/>
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
        <th>Número</th>
        <th>Candidato</th>
        <th>Partido</th>
        <th>Total</th>
    </tr>

    <%
        List<ResultadoVotacao> resultado =
                (List<ResultadoVotacao>) request.getAttribute("resultado");

        if (resultado != null) {
            for (ResultadoVotacao item : resultado) {
    %>
    <tr>
        <td><%= item.getNumeroCandidato() %></td>
        <td><%= item.getNomeCandidato() %></td>
        <td><%= item.getPartido() %></td>
        <td><%= item.getTotal() %></td>
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
