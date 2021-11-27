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
		<h1>사용자정보</h1>
		<li>${status.index+1}:${member.id},${member.password},
			${member.ban}</li>
		<h1>빌린도서</h1>
		<c:forEach var="booklist" items="${booklist}" varStatus="status">
			<li>${status.index+1}:${booklist.bname},
				${booklist.publisher},${booklist.author}</li>
		</c:forEach>
		<h1>예약도서</h1>
		<c:forEach var="rbooklist" items="${rbooklist}" varStatus="status">
			<li>${status.index+1}:${rbooklist.bname},
				${rbooklist.publisher},${rbooklist.author}</li>
		</c:forEach>
	</ul>
</body>
</html>