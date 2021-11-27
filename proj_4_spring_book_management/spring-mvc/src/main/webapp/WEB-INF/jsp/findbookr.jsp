<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
</head>
<body>
	<h1>this book is</h1>
	<ul>

		<li>${status.index+1}: ${book.bname}, ${book.publisher},
			${book.author}</li>
	</ul>
</body>
</html>