<%@include file="common/header.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <meta charset="UTF-8">
    <title>Wynik Prognoza</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css" />
    <link rel="stylesheet" type="text/css" href="css/leaflet-openweathermap.css" />
    <link rel='stylesheet' href='css/styles.css' charset="utf-8"/>
</head>
<body onload="loadMap(${lat}, ${lng})">

<div class="window">
    <%@include file="common/navigation.jspf"%>
    <div class="current-weather">
        <div class="row-1">
            <p onload=refreshTime();><span id='date-time'></span></p>
        </div>
        <div class="row-1">
            <h1>${answer.name}</h1>
        </div>
        <div class="row-1">
            <h2>${answer.mainData.temp} °C</h2>
            <img src="<c:url value="${iconLink}"/>">
            <h3>${answer.weather[0].description}</h3>
        </div>
        <div class="row-1">
            <div class="column-1">
                <h4>Ciśnienie</h4>
                <p>${answer.mainData.pressure} hPa</p>
            </div>
            <div class="column-1">
                <h4>Prędkość wiatru</h4>
                <p>${answer.windData.speed} km/h</p>
            </div>
            <div class="column-1">
                <h4>Wilgotność</h4>
                <p>${answer.mainData.humidity} %</p>
            </div>
            <div class="column-1">
                <h4>Suma opadów</h4>
                <c:if test="${empty answer.rainData.rain1h}">
                    <p>0 mm</p>
                </c:if>
                <c:if test="${not empty answer.rainData.rain1h}">
                <p>${answer.rainData.rain1h} mm</p>
                </c:if>
            </div>
        </div>
        <div class="row-1" style="margin: 20px 0px; padding: 10px 10px">
                <c:set var="previousDate" value="" />
                <c:forEach var="weatherData" items="${weatherDataList}" varStatus="status" end="7">
                    <c:set var="currentDate" value="${weatherData.dtTxt.substring(11, 16)}" />
                    <c:if test="${!previousDate.isEmpty() && !currentDate.equals(previousDate)}">
                    </c:if>
                    <c:if test="${!currentDate.equals(previousDate)}">
                    </c:if>
                    <div class="column-1">
                        <h4>${currentDate}</h4>
                        <h3>${weatherData.mainData.temp} °C</h3>
                    </div>
                    <c:set var="previousDate" value="${currentDate}" />
                </c:forEach>
        </div>
    </div>
    <h1 style="margin-bottom: 0em; font-size: 2rem; color: #05386E; font-weight: 1000; text-transform: uppercase;">Prognoza na kolejne dni</h1>
    <div class="weather-min-max">
            <c:set var="previousDate1" value="" />
            <c:set var="currentMax" value="" />
            <c:set var="currentMin" value="" />
            <c:forEach var="weatherData1" items="${weatherDataList}" varStatus="status">
                <c:set var="currentDate1" value="${weatherData1.dtTxt.substring(0, 10)}" />
                <c:if test="${previousDate1.isEmpty()}">
                    <c:set var="currentMax" value="${weatherData1.mainData.tempMax}" />
                    <c:set var="currentMin" value="${weatherData1.mainData.tempMin}" />
                </c:if>
                <c:if test="${currentDate1.equals(previousDate1)}">
                    <c:if test="${weatherData1.mainData.tempMax > currentMax}">
                        <c:set var="currentMax" value="${weatherData1.mainData.tempMax}" />
                    </c:if>
                    <c:if test="${weatherData1.mainData.tempMin < currentMin}">
                        <c:set var="currentMin" value="${weatherData1.mainData.tempMin}" />
                    </c:if>
                </c:if>
                <c:if test="${!previousDate1.isEmpty() && !currentDate1.equals(previousDate1)}">
                    <div class="column-2">
                        <h4>${currentDate1}</h4>
                        <h3>${currentMax} °C</h3>
                        <h3>${currentMin} °C</h3>
                    </div>
                    <c:set var="currentMax" value="${weatherData1.mainData.tempMax}" />
                    <c:set var="currentMin" value="${weatherData1.mainData.tempMin}" />
                </c:if>
                <c:set var="previousDate1" value="${currentDate1}" />
            </c:forEach>
    </div>

        <div style= "display: flex; justify-content: center;">
        <div id="map" style="width: 1500px; height: 700px;"></div>
        </div>

<%@include file="common/footer.jspf"%>