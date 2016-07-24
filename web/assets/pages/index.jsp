<%@ page contentType="text/html; charset=UTF-8" 
		 pageEncoding="UTF-8" errorPage="../../WEB-INF/assets/pages/error/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css"/>

<html>
	<head>
		<title>Авторизация</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	</head>
	<body onload="setLocale()">
		<%--Форма регистрации--%>
		<c:choose>
			<c:when test="${empty user}">
				<form id="login" name="loginForm" method="POST" action="controller">
					<input type="hidden" name="command" value="login" />
					<input type="hidden" name="currentPagePath" value="/assets/pages/index.jsp" />
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
				<a href="controller?command=goToAccount">Личный кабинет</a>
			</c:otherwise>
		</c:choose>

		<form name="findForm" method="POST" action="controller">
			<input type="hidden" name="command" value="selectHotel"/>
			<br/>
			Параметры поиска:<br/>
			<input type="text" name="country" placeholder="Страна" value="Belarus"/>
			<input type="text" name="city" placeholder="Город" value="Minsk"/>
			<input type="text" name="hotelName" placeholder="Отель" value="any"/><br/>
			<label for="no_rooms">Количество номеров</label>
			<select id="no_rooms" name="totalRooms">
				<option value="1" selected="selected">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
				<option value="16">16</option>
				<option value="17">17</option>
				<option value="18">18</option>
				<option value="19">19</option>
				<option value="20">20</option>
			</select>
			<br/>
			<label for="no_persons">Количество человек</label>
			<select id="no_persons" name="totalPersons">
				<option value="1" selected="selected">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
				<option value="16">16</option>
				<option value="17">17</option>
				<option value="18">18</option>
				<option value="19">19</option>
				<option value="20">20</option>
			</select>
			<br/>
			<label for="checkInDate">Дата въезда</label>
			<input id="checkInDate" type="text" name="checkInDate" value=""/>
			<label for="checkOutDate">Дата отъезда</label>
			<input id="checkOutDate" type="text" name="checkOutDate" value=""/>
			<input type="submit" value="Искать">

		</form>

		<form name="langForm" method="POST" action="controller">
			<input type="hidden" name="command" value="setLocale"/>
			<label for="language">Локаль</label>
			<select id="language" name="language">
				<option value="ru">RU</option>
				<option value="en">EN</option>
			</select>
			<input type="submit" value="Ввод">
		</form>

		${operationMessage}


		<div id="test">
			<label for="num1">Число1</label>
			<input type="text" id="num1" value="">
			<label for="num2">Число2</label>
			<input type="text" id="num2" value="">
			<p id="rez">rez</p>
			<p id="tsst">test</p>
		</div>
	<button type="button" onclick="calcul()">Push me</button>
	<c:set scope="request" value="${exprr}" var="prop"/>
	<c:out value="${requestScope.prop}"/>
	</body>
	<script type="text/javascript" src="assets/scripts/script.js"></script>
</html>