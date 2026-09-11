<%@ page import="java.util.List" %>
<%@ page import="votacao.*" %>
<%@ page import="appVoto.Eleicao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Candidaturas"/>
    <jsp:param name="current" value="candidaturas"/>
</jsp:include>

<h1 class="page-title">Candidaturas</h1>
<p class="page-lead">Associe eleitor, partido, eleição, cargo e número de urna.</p>

<%
    String erro = (String) request.getAttribute("erro");
    List<Candidatura> candidaturas = (List<Candidatura>) request.getAttribute("candidaturas");
    List<Eleitor> eleitores = (List<Eleitor>) request.getAttribute("eleitores");
    List<Partido> partidos = (List<Partido>) request.getAttribute("partidos");
    List<Eleicao> eleicoes = (List<Eleicao>) request.getAttribute("eleicoes");
    List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");
    if (erro != null) {
%>
<p class="alert alert-error"><%= erro %></p>
<%
    }
%>

<section class="panel">
    <h2>Inserir / alterar</h2>
    <form method="post" action="candidaturas" class="form-grid">
        <label class="field">ID <span class="muted">(vazio para inserir)</span>
            <input type="text" name="id"/>
        </label>
        <label class="field">Eleitor
            <select name="eleitorId" required>
                <%
                    if (eleitores != null) {
                        for (Eleitor eleitor : eleitores) {
                %>
                <option value="<%= eleitor.getId() %>"><%= eleitor.getNome() %> - <%= eleitor.getTitulo() %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Partido
            <select name="partidoId" required>
                <%
                    if (partidos != null) {
                        for (Partido partido : partidos) {
                %>
                <option value="<%= partido.getId() %>"><%= partido.getSigla() %> - <%= partido.getNome() %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Eleição
            <select name="eleicaoId" required>
                <%
                    if (eleicoes != null) {
                        for (Eleicao eleicao : eleicoes) {
                %>
                <option value="<%= eleicao.getId() %>"><%= eleicao.getNome() %> - <%= eleicao.getAno() %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Cargo
            <select name="cargoId" required>
                <%
                    if (cargos != null) {
                        for (Cargo cargo : cargos) {
                %>
                <option value="<%= cargo.getId() %>"><%= cargo.getNome() %> <%= cargo.getUf() == null ? "(Nacional)" : "(" + cargo.getUf() + ")" %></option>
                <%
                        }
                    }
                %>
            </select>
        </label>
        <label class="field">Número
            <input type="number" name="numero" required/>
        </label>
        <label class="field field-check">
            <input type="checkbox" name="ativa" checked/> Ativa
        </label>
        <div class="actions">
            <button type="submit">Salvar</button>
        </div>
    </form>
</section>

<section class="table-wrap">
    <h2>Lista</h2>
    <div class="table-scroll">
        <table>
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
                <td><%= candidatura.getNomeCargo() %> <%= candidatura.getUfCargo() == null ? "(Nacional)" : "(" + candidatura.getUfCargo() + ")" %></td>
                <td><%= candidatura.getNumero() %></td>
                <td><span class="badge <%= candidatura.isAtiva() ? "badge-ok" : "badge-off" %>"><%= candidatura.isAtiva() ? "Sim" : "Não" %></span></td>
                <td>
                    <form class="inline-form" method="post" action="candidaturas">
                        <input type="hidden" name="acao" value="remover"/>
                        <input type="hidden" name="id" value="<%= candidatura.getId() %>"/>
                        <button class="btn-danger" type="submit" onclick="return confirm('Confirma remoção?')">Remover</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (candidaturas == null || candidaturas.isEmpty()) { %>
        <p class="empty">Nenhuma candidatura cadastrada.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
