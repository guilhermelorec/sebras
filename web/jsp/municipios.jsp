<%@ page import="java.util.List" %>
<%@ page import="votacao.Municipio" %>
<%@ page import="votacao.UnidadeFederativa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Municípios</title>
</head>
<body>

<h1>Municípios</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    UnidadeFederativa[] ufs = (UnidadeFederativa[]) request.getAttribute("ufs");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar município</h2>

<form method="post" action="municipios">
    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Nome:
        <input type="text" name="nome" required/>
    </label>
    <br/>

    <label>UF:
        <select name="uf" required>
            <%
                if (ufs != null) {
                    for (UnidadeFederativa uf : ufs) {
            %>
            <option value="<%= uf.name() %>">
                <%= uf.name() %> - <%= uf.getNome() %>
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

<h2>Lista de municípios</h2>

<table border="1" cellpadding="4">
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
            <form method="post" action="municipios">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= municipio.getId() %>"/>
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
