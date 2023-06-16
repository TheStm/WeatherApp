<%@include file="common/header.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <meta charset="UTF-8">
    <title>Dane historyczne</title>
</head>
<body>
<div class="window">
    <%@include file="common/navigation.jspf"%>
    <div class="center">
        <form:form method="POST" action="getStation" modelAttribute="station">
           <table>
             <tr>
               <td><p>Wybierz stację:</p></td>
               <td><form:select path="name" items="${stationList}" id="station"/></td>
             </tr>

               <tr>
                   <td><p>Wybierz datę początkową:</p></td>
                   <td><input type="text" id="beginDate" name="beginDate" class="datepicker"></td>
               </tr>

               <tr>
                   <td><p>Wybierz datę końcową:</p></td>
                   <td><input type="text" id="endDate" name="endDate" class="datepicker"></td>
               </tr>

             <tr>
               <td></td>
               <td><input class="button button-primary" type="submit" value="Prześlij"></td>
             </tr>
           </table>
         </form:form>
    </div>
</div>


<%@include file="common/footer.jspf"%>


<script type="text/javascript">
    $(document).ready(function() {
        $('.datepicker').datepicker({
            format: 'dd-mm-yyyy',
        });
    });
</script>
