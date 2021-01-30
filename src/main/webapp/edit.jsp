<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meal edit</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>

</head>
<body>
<h2>Добавить еду</h2>
<form method="POST" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    Дата/время : <input type="datetime-local" name="date" value="${meal.dateTime}"/> <br/><br/>
    Описание : <input type="text" name="description" value="${meal.description}"/> <br/><br/>
    Калории : <input type="text" name="calories" value="${meal.calories}"/> <br/><br/>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="reset">Отменить</button>
</form>
</body>
</html>
