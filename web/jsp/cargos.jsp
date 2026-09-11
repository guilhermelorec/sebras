<%@ page import="java.util.List" %>
<%@ page import="votacao.Cargo" %>
<%@ page import="votacao.UnidadeFederativa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cargos</title>
</head>
<body>

<h1>Cargos</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar cargo</h2>

<form method="post" action="cargos">

    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Nome:
        <input type="text" name="nome" required/>
    </label>
    <br/>

    <label>UF:
        <select name="uf">
            <option value="">Nacional</option>
            <%
                for (UnidadeFederativa uf : UnidadeFederativa.values()) {
            %>
            <option value="<%= uf.name() %>">
                <%= uf.name() %> - <%= uf.getNome() %>
            </option>
            <%
                }
            %>
        </select>
    </label>
    <br/>

    <label>Permite segundo turno:
        <input type="checkbox" name="permiteSegundoTurno"/>
    </label>
    <br/>

    <button type="submit">Salvar</button>

</form>

<h2>Lista de cargos</h2>

<table border="1" cellpadding="4">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>UF</th>
        <th>Segundo Turno</th>
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
        <td><%= cargo.isPermiteSegundoTurno() ? "Sim" : "Não" %></td>
        <td>
            <form method="post" action="cargos">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= cargo.getId() %>"/>
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
