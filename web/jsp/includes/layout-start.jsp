<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String title = request.getParameter("title");
    String current = request.getParameter("current");
    if (title == null || title.isBlank()) {
        title = "SEBRAS";
    }
    if (current == null) {
        current = "";
    }
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= title %> · SEBRAS</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Fraunces:opsz,wght@9..144,560;9..144,700&family=Source+Sans+3:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= ctx %>/css/sebras.css">
</head>
<body>
<header class="topbar">
    <div class="topbar-inner">
        <a class="brand" href="<%= ctx %>/">
            <span class="brand-mark" aria-hidden="true">
                <svg width="26" height="26" viewBox="0 0 26 26" fill="none">
                    <rect x="4" y="7" width="18" height="14" stroke="#C4A35A" stroke-width="1.6"/>
                    <path d="M4 11H22" stroke="#C4A35A" stroke-width="1.6"/>
                    <path d="M13 3V7" stroke="#C4A35A" stroke-width="1.6"/>
                    <circle cx="13" cy="16" r="2.2" fill="#C4A35A"/>
                </svg>
            </span>
            <span>
                <span class="brand-kicker">Sistema eleitoral</span>
                <span class="brand-name">SEBRAS</span>
            </span>
        </a>
        <nav class="nav" aria-label="Principal">
            <a class="<%= "home".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/">Início</a>
            <a class="<%= "partidos".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/partidos">Partidos</a>
            <a class="<%= "eleitores".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/eleitores">Eleitores</a>
            <a class="<%= "municipios".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/municipios">Municípios</a>
            <a class="<%= "zonas".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/zonas">Zonas</a>
            <a class="<%= "secoes".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/secoes">Seções</a>
            <a class="<%= "eleicoes".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/eleicoes">Eleições</a>
            <a class="<%= "cargos".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/cargos">Cargos</a>
            <a class="<%= "candidaturas".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/candidaturas">Candidaturas</a>
            <a class="<%= "votar".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/votar">Votar</a>
            <a class="<%= "resultado".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/resultado">Resultado</a>
            <a class="<%= "abstencao".equals(current) ? "is-active" : "" %>" href="<%= ctx %>/abstencao">Abstenção</a>
        </nav>
    </div>
</header>
<main class="page">
    <div class="wrap">
