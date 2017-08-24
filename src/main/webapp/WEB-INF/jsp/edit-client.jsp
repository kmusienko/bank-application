<%--
  Created by IntelliJ IDEA.
  User: Kostya
  Date: 11.03.2017
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Edit client</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="col-xs-4 centered">
        <h3>Edit client:</h3>

        <c:url value="/client/edit" var="editUrl"/>
        <form action="${editUrl}" method="post">

            <input type="hidden" name="id" value="${client.id}">

            <div class="form-group">
                <label for="name">Name:</label>
                <input id="name" type="text" class="form-control" name="name" value="${client.name}">
            </div>

            <div class="form-group">
                <label>Gender:</label>
                <input type="radio" name="gender" value="FEMALE" ${client.gender == "FEMALE" ? "checked" : ""}/>Женский
                <input type="radio" name="gender" value="MALE" ${client.gender == "MALE" ? "checked" : ""}/>Мужской
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input id="email" type="text" class="form-control" name="email" value="${client.email}">
            </div>
            <div class="form-group">
                <label for="tel">Phone:</label>
                <input id="tel" type="text" class="form-control" name="tel" value="${client.tel}">
            </div>
            <div class="form-group">
                <label for="city">City:</label>
                <input id="city" type="text" class="form-control" name="city" value="${client.city}">
            </div>

            <input type="submit" class="btn btn-success" value="Submit">
        </form>
    </div>
</div>

</body>
</html>
