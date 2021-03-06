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
<%@include file="../../../../assets/pages/inputs/top.jsp" %>
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
