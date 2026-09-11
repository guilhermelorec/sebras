<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidaturas</title>
</head>
<body>

<h1>Candidaturas</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Candidatura> candidaturas = (List<Candidatura>) request.getAttribute("candidaturas");
    List<Eleitor> eleitores = (List<Eleitor>) request.getAttribute("eleitores");
    List<Partido> partidos = (List<Partido>) request.getAttribute("partidos");
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");
    List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar candidatura</h2>

<form method="post" action="candidaturas">

    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Eleitor:
        <select name="eleitorId" required>
            <%
                if (eleitores != null) {
                    for (Eleitor eleitor : eleitores) {
            %>
            <option value="<%= eleitor.getId() %>">
                <%= eleitor.getNome() %> - <%= eleitor.getTitulo() %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <label>Partido:
        <select name="partidoId" required>
            <%
                if (partidos != null) {
                    for (Partido partido : partidos) {
            %>
            <option value="<%= partido.getId() %>">
                <%= partido.getSigla() %> - <%= partido.getNome() %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <label>Eleição:
        <select name="eleicaoId" required>
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

    <label>Cargo:
        <select name="cargoId" required>
            <%
                if (cargos != null) {
                    for (Cargo cargo : cargos) {
            %>
            <option value="<%= cargo.getId() %>">
                <%= cargo.getNome() %>
                <%= cargo.getUf() == null ? "(Nacional)" : "(" + cargo.getUf() + ")" %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </label>
    <br/>

    <label>Número:
        <input type="number" name="numero" required/>
    </label>
    <br/>

    <label>Ativa:
        <input type="checkbox" name="ativa" checked/>
    </label>
    <br/>

    <button type="submit">Salvar</button>

</form>

<h2>Lista de candidaturas</h2>

<table border="1" cellpadding="4">
    <tr>
        <th>ID</th>
        <th>Eleitor</th>
        <th>Partido</th>
        <th>Eleição</th>
        <th>Cargo</th>
        <th>Número</th>
        <th>Ativa</th>
        <th>Ações</th>
    </tr>

    <%
        if (candidaturas != null) {
            for (Candidatura candidatura : candidaturas) {
    %>
    <tr>
        <td><%= candidatura.getId() %></td>
        <td><%= candidatura.getNomeEleitor() %></td>
        <td><%= candidatura.getSiglaPartido() %></td>
        <td><%= candidatura.getNomeEleicao() %></td>
        <td>
            <%= candidatura.getNomeCargo() %>
            <%= candidatura.getUfCargo() == null ? "(Nacional)" : "(" + candidatura.getUfCargo() + ")" %>
        </td>
        <td><%= candidatura.getNumero() %></td>
        <td><%= candidatura.isAtiva() ? "Sim" : "Não" %></td>
        <td>
            <form method="post" action="candidaturas">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= candidatura.getId() %>"/>
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
