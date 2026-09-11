<%@ page import="java.util.List" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleições</title>
</head>
<body>

<h1>Eleições</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");

    if (erro != null) {
%>	
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar eleição</h2>

<form method="post" action="eleicoes">

    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Nome:
        <input type="text" name="nome" required/>
    </label>
    <br/>

    <label>Ano:
        <input type="number" name="ano" required/>
    </label>
    <br/>

    <label>Data Turno 1:
        <input type="date" name="dataTurno1" required/>
    </label>
    <br/>

    <label>Data Turno 2:
        <input type="date" name="dataTurno2"/>
    </label>
    <br/>

    <label>Turno atual:
        <select name="turnoAtual">
            <option value="1">1º Turno</option>
            <option value="2">2º Turno</option>
        </select>
    </label>
    <br/>

    <label>Segundo turno habilitado:
        <input type="checkbox" name="segundoTurnoHabilitado"/>
    </label>
    <br/>

    <label>Ativa:
        <input type="checkbox" name="ativa" checked/>
    </label>
    <br/>

    <button type="submit">Salvar</button>

</form>

<h2>Lista de eleições</h2>

<table border="1" cellpadding="4">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Ano</th>
        <th>Turno 1</th>
        <th>Turno 2</th>
        <th>Turno Atual</th>
        <th>Segundo Turno</th>
        <th>Ativa</th>
        <th>Ações</th>
    </tr>

    <%
        if (eleicoes != null) {
            for (Eleicao eleicao : eleicoes) {
    %>
    <tr>
        <td><%= eleicao.getId() %></td>
        <td><%= eleicao.getNome() %></td>
        <td><%= eleicao.getAno() %></td>
        <td><%= eleicao.getDataTurno1() %></td>
        <td><%= eleicao.getDataTurno2() %></td>
        <td><%= eleicao.getTurnoAtual() %></td>
        <td><%= eleicao.isSegundoTurnoHabilitado() ? "Sim" : "Não" %></td>
        <td><%= eleicao.isAtiva() ? "Sim" : "Não" %></td>
        <td>
            <form method="post" action="eleicoes">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= eleicao.getId() %>"/>
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
