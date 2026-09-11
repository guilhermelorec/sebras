<%@ page import="votacao.Partido" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Partidos</title>
</head>
<body>

<h1>Partidos</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Partido> partidos = (List<Partido>) request.getAttribute("partidos");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar partido</h2>

<form method="post" action="partidos">
    <label>ID (vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Número:
        <input type="number" name="numero" required/>
    </label>
    <br/>

    <label>Sigla:
        <input type="text" name="sigla" required/>
    </label>
    <br/>

    <label>Nome:
        <input type="text" name="nome" required/>
    </label>
    <br/>

    <label>Ativo:
        <input type="checkbox" name="ativo" checked/>
    </label>
    <br/>

    <button type="submit">Salvar</button>
</form>

<h2>Lista de partidos</h2>

<table border="1" cellpadding="4">
    <tr>
        <th>ID</th>
        <th>Número</th>
        <th>Sigla</th>
        <th>Nome</th>
        <th>Ativo</th>
        <th>Ações</th>
    </tr>

    <%
        if (partidos != null) {
            for (Partido partido : partidos) {
    %>
    <tr>
        <td><%= partido.getId() %></td>
        <td><%= partido.getNumero() %></td>
        <td><%= partido.getSigla() %></td>
        <td><%= partido.getNome() %></td>
        <td><%= partido.isAtivo() ? "Sim" : "Não" %></td>
        <td>
            <form method="post" action="partidos">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= partido.getId() %>"/>
                <button type="submit"
                        onclick="return confirm('Confirma remoção?')">
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
