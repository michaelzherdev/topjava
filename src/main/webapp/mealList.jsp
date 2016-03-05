<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal List</title>
    <style>
        .green {
            color: green;
        }

        .red    {
            color: red;
        }
    </style>
</head>
<body>
<h2>Meal list</h2>

<table class="table table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>

    <c:forEach var="m" items="${listWithExceed}">
        <c:choose>
            <c:when test="${m.exceed}">
                <tr class="red">
                    <td>${m.id}</td>
                    <td>${m.dateTime}</td>
                    <td>${m.description}</td>
                    <td>${m.calories}</td>
                </tr>

            </c:when>
            <c:otherwise>
                <tr class="green">
                    <td>${m.id}</td>
                    <td>${m.dateTime}</td>
                    <td>${m.description}</td>
                    <td>${m.calories}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
