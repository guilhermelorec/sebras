<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seções Eleitorais</title>
</head>
<body>

<h1>Seções Eleitorais</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Secao> secoes = (List<Secao>) request.getAttribute("secoes");
    List<Zona> zonas = (List<Zona>) request.getAttribute("zonas");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar seção</h2>

<form method="post" action="secoes">

    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Número:
        <input type="number" name="numero" required/>
    </label>
    <br/>

    <label>Zona:
        <select name="zonaId" required>
            <%
                if (zonas != null) {
                    for (Zona zona : zonas) {
            %>
            <option value="<%= zona.getId() %>">
                Zona <%= zona.getNumero() %> - <%= zona.getNomeMunicipio() %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <label>Local:
        <input type="text" name="local"/>
    </label>
    <br/>

    <button type="submit">Salvar</button>

</form>

<h2>Lista de seções</h2>

<table border="1" cellpadding="4">
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
            <form method="post" action="secoes">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= secao.getId() %>"/>
                <button type="submit" onclick="return confirm('Confirma remoção?')">
                    Remover
                </button>
            </form>
        </td>
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
