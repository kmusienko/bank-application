<%--
  Created by IntelliJ IDEA.
  User: Kostya
  Date: 11.03.2017
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add an account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="col-xs-4 centered">
        <h3>Add account:</h3>
        <c:url value="/client/account/add?clientId=${clientId}" var="editUrl"/>
        <form action="${editUrl}" method="post">
            <input type="hidden" name="clientId" value="${clientId}">
            <input type="hidden" name="clientName" value="${clientName}">
            <div class="form-group">
                <label>Account type:</label>
                <input type="radio" name="accountType" value="SAVING" }/>SAVING
                <input type="radio" name="accountType" value="CHECKING" }/>CHECKING
            </div>
            <div class="form-group">
                <label for="balance">Balance:</label>
                <input id="balance" type="text" class="form-control" name="balance">
            </div>
            <div class="form-group">
                <label for="overdraft">Overdraft:</label>
                <input id="overdraft" type="text" class="form-control" name="overdraft">
            </div>
            <input type="submit" class="btn btn-success" value="Submit">
        </form>
        <c:url value="/client?id=${clientId}" var="backToClientUrl"/>
        <a href="${backToClientUrl}" class="btn btn-primary" role="button">Back to client info</a>
    </div>
</div>
</body>
</html>
