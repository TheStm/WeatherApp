<%@include file="common/header.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Moje Konto</title>
</head>
<body>
<div class="window">
    <%@include file="common/navigation.jspf"%>
    <div class="container-user">
        <h1>Dane użytkownika</h1>

        <label for="name">Imię użytkownika</label>
        <p id="name" name="name">${name}</p><br>

        <label for="username">Nazwa użytkownika</label>
        <p id="username" name="username">${username}</p><br>

        <label for="email">Adres poczty elektonicznej</label>
        <p id="email" name="email">${email}</p><br>
    </div>
</div>
<%@include file="common/footer.jspf"%>
