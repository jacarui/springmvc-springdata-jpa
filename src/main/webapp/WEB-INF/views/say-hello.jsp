<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello</title>
</head>
<body>
	<form method="post" action="say-hello">
		Enter username: <input type="text" id="username" name="username"
			value="<c:out value="${username}"/>">
		<div id="message">
			<c:out value="${message}" />
		</div>
		<p>
			<input type="submit" id="submit" value="Submit">
		</p>
	</form>
	
	<table>
		<c:forEach var="user" items="${usersPage.content}">
			<tr>
				<td><c:out value="${user.username}"></c:out></td>
			</tr>
		</c:forEach>
	</table>
	<c:out value="Pag ${usersPage.number + 1} de ${usersPage.totalPages}"/>

</body>
</html>
