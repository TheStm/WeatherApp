<%@include file="common/header.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <meta charset="UTF-8">
    <title>Prognoza Pogody</title>
</head>
<body>
<div class="window">
    <%@include file="common/navigation.jspf"%>
    <div class="center">
        <form action="/result" method="get">
            <input class="search-input" placeholder="Wpisz lokalizację..." type="text" value="" name="city" id="search" autocomplete="off" role="textbox">
            <input class="button button-primary" type="submit" value="Prześlij">
        </form>
    </div>
</div>

<%@include file="common/footer.jspf"%>