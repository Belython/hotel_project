<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css"/>
<html>
<head>
    <title>Результаты поиска</title>
</head>
<body>
<%--Форма регистрации--%>
<c:choose>
    <c:when test="${empty user}">
        <form id="login" name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login" />
            <input type="hidden" name="currentPagePath" value="/WEB-INF/assets/pages/client/selectHotel.jsp" />
            <h1>Форма входа</h1>
            <fieldset id="inputs">
                <input id="username" type="text" name="login" placeholder="Логин" autofocus required>
                <input id="password" type="password" name="password" placeholder="Пароль" required>
            </fieldset>
            <fieldset id="actions">
                <input type="submit" id="submit" value="ВОЙТИ">
                <a href="">Забыли пароль?</a><a href="controller?command=gotoregistration">Регистрация</a>
            </fieldset>
                ${errorLoginOrPassword}<br />
        </form>
    </c:when>
    <c:otherwise>
        <p>Вы вошли как ${user.login}</p>
        <a href="controller?command=logout&currentPagePath=/WEB-INF/assets/pages/client/selectHotel.jsp">Выйти из системы</a>
    </c:otherwise>
</c:choose>

<form name="resultsForm" method="POST" action="controller">
    <c:forEach var="hotel" items="${hotelsList}">
        <a href="controller?command=selectRoom&selectedHotel=${hotel.hotelId}">
            Отель ${hotel.hotelName}
            Осталось ${hotel.roomsCount}
        </a><br/>
    </c:forEach>


    ${operationMessage}
    ${errorUserExists} <br/>
    <a href="controller?command=back">Вернуться обратно</a>
</form>
</body>
</html>
