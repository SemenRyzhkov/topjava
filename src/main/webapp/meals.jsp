<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UserMeals</title>
</head>
<body>
<h1>Meals</h1>
<p><a href="meals?action=insert">Добавить</a></p>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Action</th>

    </tr>
    <c:forEach items="${mealToList}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:${mealTo.excess ? 'red' : 'green'}">
            <td>${mealTo.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))} </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&mealId=${mealTo.id}">Обновить</a></td>
            <td><a href="meals?action=delete&mealId=${mealTo.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


