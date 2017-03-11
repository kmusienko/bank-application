<%--
  Created by IntelliJ IDEA.
  User: Kostya
  Date: 11.03.2017
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h3>Account:</h3>
<h4>Client: ${client.name}</h4>
<h4>Account type: ${account.accountType}</h4>
<h4>Balance: ${account.balance}</h4>
<c:if test="${account.accountType=='CHECKING'}">
    <h4>Overdraft: ${account.overdraft}</h4>
</c:if>
<form class="form-inline">
    <div class="form-group">
        <label class="sr-only" for="amount">Amount (in dollars)</label>
        <div class="input-group">
            <div class="input-group-addon">$</div>
            <input type="text" class="form-control" id="amount" name="amount" placeholder="Amount">
            <div class="input-group-addon">.00</div>
        </div>
    </div>
    <input type="hidden" name="clientId" value="${client.id}">
    <input type="hidden" name="id" value="${account.id}">
    <input formaction="/client/account/deposit" type="submit" class="btn btn-success" value="Deposit">
    <input formaction="/client/account/withdraw" type="submit" class="btn btn-warning" value="Withdraw">
</form>
    <c:url value="/client?id=${client.id}" var="allClientsUrl"/>
    <a href="${allClientsUrl}" class="btn btn-link" role="button">Back to client info</a>
</div>
</body>
</html>
