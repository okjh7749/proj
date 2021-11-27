<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
</head>
<body>
	<h1>your borrow Book List</h1>
	<ul>
		<c:forEach var="booklist" items="${booklist}" varStatus="status">
			<li>${status.index+1} : ${booklist.bname}, ${booklist.publisher},${booklist.author}</li>
		</c:forEach>
	</ul>
</body>
</html>