<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../error/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css"/>
<html>
<head>
    <title>Поиск номеров</title>
</head>
<body>

<%--Форма регистрации--%>
<c:choose>
    <c:when test="${empty user}">
        <form id="login" name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login" />
            <input type="hidden" name="currentPagePath" value="/WEB-INF/assets/pages/client/selectRoom.jsp" />
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
        <a href="controller?command=logout">Выйти из системы</a>
    </c:otherwise>
</c:choose>

<form name="searchForm" method="POST" action="controller">
    <c:forEach var="roomType" items="${selectedHotel.roomTypes}" >
    <a href="controller?command=makeOrder&roomTypeId=${roomType.id}">
        ${roomType.roomTypeName}
        ${selectedHotel.roomTypesCount.get(roomType)}
    </a><br/>
</c:forEach>
    ${par}
    ${operationMessage}
    ${errorUserExists} <br />
    <a href="controller?command=back">Вернуться обратно</a>
</form>
</body>
</html>
