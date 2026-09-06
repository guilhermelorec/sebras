<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Votar</title>
</head>
<body>

<h1>Registrar voto</h1>

<%
    String mensagem = (String) request.getAttribute("mensagem");
    String erro = (String) request.getAttribute("erro");

    if (mensagem != null) {
%>
<p style="color: green;"><%= mensagem %></p>
<%
    }

    if (erro != null) {
%>
<p style="color: red;"><%= erro %></p>
<%
    }
%>

<form method="post" action="votar">

    <label>Eleitor ID:
        <input type="number" name="eleitorId" required/>
    </label>
    <br/>

    <label>Candidatura ID:
        <input type="number" name="candidaturaId" required/>
    </label>
    <br/>

    <label>Eleição ID:
        <input type="number" name="eleicaoId" required/>
    </label>
    <br/>

    <label>Turno:
        <select name="turno">
            <option value="1">1º Turno</option>
            <option value="2">2º Turno</option>
        </select>
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

    <button type="submit">Confirmar voto</button>

</form>

<br/>
<a href="${pageContext.request.contextPath}/">Voltar</a>

</body>
</html>
