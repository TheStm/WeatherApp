<%@include file="common/header.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Logowanie</title>
</head>
<body>
<div class="window">
    <div class="container-login">
        <h1>Logowanie</h1>

        <form method="POST" action="/login">
            <label for="username">Nazwa użytkownika</label>
            <input class="search-input" type="text" id="username" name="username" required><br><br>

            <label for="password">Hasło</label>
            <input class="search-input" type="password" id="password" name="password" required><br><br>

            <%-- Wyświetlanie komunikatu błędu (jeśli istnieje) --%>
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
            <input class="button button-primary" type="submit" value="Zaloguj">
            <a class="button button-primary" href="/register">Zarejestruj się</a>
        </form>
    </div>
</div>
<%@include file="common/footer.jspf"%>
