<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/jsp/includes/layout-start.jsp">
    <jsp:param name="title" value="Início"/>
    <jsp:param name="current" value="home"/>
</jsp:include>

<section class="hero">
    <div>
        <p class="brand-kicker">Mesa eleitoral digital</p>
        <h1 class="page-title">Sistema de votação e apuração</h1>
        <p class="page-lead">Cadastre o território, partidos, eleitores e candidaturas. Registre o voto e consulte o resultado por zona.</p>
    </div>
    <aside class="hero-aside">
        <strong>Urna pronta</strong>
        <p>Siga a ordem: município, zona, seção, eleitor, eleição, candidatura e voto.</p>
    </aside>
</section>

<section class="card-grid">
    <a class="card" href="${pageContext.request.contextPath}/partidos">
        <span>Cadastro</span>
        <div>
            <h2>Partidos</h2>
            <p>Número, sigla e situação das legendas.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/eleitores">
        <span>Cadastro</span>
        <div>
            <h2>Eleitores</h2>
            <p>Título, zona, seção e comparecimento.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/municipios">
        <span>Território</span>
        <div>
            <h2>Municípios</h2>
            <p>Cidades e unidades da federação.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/zonas">
        <span>Território</span>
        <div>
            <h2>Zonas</h2>
            <p>Zonas eleitorais por município.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/secoes">
        <span>Território</span>
        <div>
            <h2>Seções</h2>
            <p>Locais de votação de cada zona.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/eleicoes">
        <span>Pleito</span>
        <div>
            <h2>Eleições</h2>
            <p>Ano, turnos e status da eleição.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/cargos">
        <span>Pleito</span>
        <div>
            <h2>Cargos</h2>
            <p>Cargos nacionais ou por UF.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/candidaturas">
        <span>Pleito</span>
        <div>
            <h2>Candidaturas</h2>
            <p>Número de urna, partido e cargo.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/votar">
        <span>Urna</span>
        <div>
            <h2>Votar</h2>
            <p>Registrar o voto do eleitor.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/resultado">
        <span>Apuração</span>
        <div>
            <h2>Resultado</h2>
            <p>Totais por zona, eleição e turno.</p>
        </div>
    </a>
    <a class="card" href="${pageContext.request.contextPath}/abstencao">
        <span>Apuração</span>
        <div>
            <h2>Abstenção</h2>
            <p>Presentes e ausentes por zona.</p>
        </div>
    </a>
</section>

<jsp:include page="/jsp/includes/layout-end.jsp"/>
