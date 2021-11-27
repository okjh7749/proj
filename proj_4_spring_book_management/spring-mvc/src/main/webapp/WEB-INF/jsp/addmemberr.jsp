<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
</head>
<body>
	<h1>Member List</h1>
	<ul>
		<c:forEach var="member" items="${members}" varStatus="status">
			<li>${status.index+1} : ${member.id}, ${member.password},
				${member.ban}</li>
		</c:forEach>
	</ul>
</body>
</html>