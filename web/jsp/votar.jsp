<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Votar"/>
    <jsp:param name="current" value="votar"/>
</jsp:include>

<h1 class="page-title">Registrar voto</h1>
<p class="page-lead">Informe eleitor, candidatura, eleição, turno, zona e seção.</p>

<%
    String mensagem = (String) request.getAttribute("mensagem");
    String erro = (String) request.getAttribute("erro");
    if (mensagem != null) {
%>
<p class="alert alert-ok"><%= mensagem %></p>
<%
    }
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Urna</h2>
    <form method="post" action="votar" class="form-grid">
        <label class="field">Eleitor ID
            <input type="number" name="eleitorId" required/>
        </label>
        <label class="field">Candidatura ID
            <input type="number" name="candidaturaId" required/>
        </label>
        <label class="field">Eleição ID
            <input type="number" name="eleicaoId" required/>
        </label>
        <label class="field">Turno
            <select name="turno">
                <option value="1">1º turno</option>
                <option value="2">2º turno</option>
            </select>
        </label>
        <label class="field">Zona ID
            <input type="number" name="zonaId" required/>
        </label>
        <label class="field">Seção ID
            <input type="number" name="secaoId" required/>
        </label>
        <div class="actions">
            <button type="submit">Confirmar voto</button>
        </div>
    </form>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
