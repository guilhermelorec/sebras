<%@ page import="votacao.Eleitor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleitores</title>
</head>
<body>

<h1>Eleitores</h1>

<%
    String erro = (String) request.getAttribute("erro");
    List<Eleitor> eleitores = (List<Eleitor>) request.getAttribute("eleitores");

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<h2>Inserir / Alterar eleitor</h2>

<form method="post" action="eleitores">
    <label>ID (deixe vazio para inserir):
        <input type="text" name="id"/>
    </label>
    <br/>

    <label>Título:
        <input type="text" name="titulo" required/>
    </label>
    <br/>

    <label>CPF:
        <input type="text" name="cpf"/>
    </label>
    <br/>

    <label>Nome:
        <input type="text" name="nome" required/>
    </label>
    <br/>

    <label>Zona ID:
        <input type="number" name="zonaId" required/>
    </label>
    <br/>

    <label>Seção ID:
        <input type="number" name="secaoId" required/>
    </label>
    <br/>

    <button type="submit">Salvar</button>
</form>

<h2>Lista de eleitores</h2>

<table border="1" cellpadding="4">
    <tr>
        <th>ID</th>
        <th>Título</th>
        <th>CPF</th>
        <th>Nome</th>
        <th>Zona</th>
        <th>Seção</th>
        <th>Ativo</th>
        <th>Votou</th>
        <th>Ações</th>
    </tr>

    <%
        if (eleitores != null) {
            for (Eleitor eleitor : eleitores) {
    %>
    <tr>
        <td><%= eleitor.getId() %></td>
        <td><%= eleitor.getTitulo() %></td>
        <td><%= eleitor.getCpf() %></td>
        <td><%= eleitor.getNome() %></td>
        <td><%= eleitor.getZonaId() %></td>
        <td><%= eleitor.getSecaoId() %></td>
        <td><%= eleitor.isAtivo() ? "Sim" : "Não" %></td>
        <td><%= eleitor.isVotou() ? "Sim" : "Não" %></td>
        <td>
            <form method="post" action="eleitores">
                <input type="hidden" name="acao" value="remover"/>
                <input type="hidden" name="id" value="<%= eleitor.getId() %>"/>
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
