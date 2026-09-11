<%@ page import="java.util.List" %>
<%@ page import="votacao.ResultadoVotacao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Resultado"/>
    <jsp:param name="current" value="resultado"/>
</jsp:include>

<h1 class="page-title">Resultado por zona</h1>
<p class="page-lead">Consulte os totais de uma zona, eleição e turno.</p>

<section class="panel">
    <h2>Consulta</h2>
    <form method="get" action="resultado" class="form-grid">
        <label class="field">Zona ID
            <input type="number" name="zonaId" value="<%= request.getParameter("zonaId") == null ? "" : request.getParameter("zonaId") %>" required/>
        </label>
        <label class="field">Eleição ID
            <input type="number" name="eleicaoId" value="<%= request.getParameter("eleicaoId") == null ? "" : request.getParameter("eleicaoId") %>" required/>
        </label>
        <label class="field">Turno
            <select name="turno">
                <option value="1">1º turno</option>
                <option value="2">2º turno</option>
            </select>
        </label>
        <div class="actions">
            <button type="submit">Consultar</button>
        </div>
    </form>
</section>

<section class="table-wrap">
    <h2>Apuração</h2>
    <div class="table-scroll">
        <table>
            <tr>
                <th>Número</th>
                <th>Candidato</th>
                <th>Partido</th>
                <th>Total</th>
            </tr>
            <%
                List<ResultadoVotacao> resultado = (List<ResultadoVotacao>) request.getAttribute("resultado");
                if (resultado != null) {
                    for (ResultadoVotacao item : resultado) {
            %>
            <tr>
                <td><%= item.getNumeroCandidato() %></td>
                <td><%= item.getNomeCandidato() %></td>
                <td><%= item.getPartido() %></td>
                <td><%= item.getTotal() %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% if (resultado == null || resultado.isEmpty()) { %>
        <p class="empty">Nenhum resultado para os filtros informados.</p>
        <% } %>
    </div>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
