<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zonas Eleitorais</title>
</head>
<body>

<h1>Zonas Eleitorais</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Zona> zonas = (List<Zona>) request.getAttribute("zonas");
    List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar zona</h2>

<form method="post" action="zonas">

    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Número:
        <input type="number" name="numero" required/>
    </label>
    <br/>

    <label>Município:
        <select name="municipioId" required>
            <%
                if (municipios != null) {
                    for (Municipio municipio : municipios) {
            %>
            <option value="<%= municipio.getId() %>">
                <%= municipio.getNome() %> - <%= municipio.getUf() %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <button type="submit">Salvar</button>

</form>

<h2>Lista de zonas</h2>

<table border="1" cellpadding="4">
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
            <form method="post" action="zonas">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= zona.getId() %>"/>
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
