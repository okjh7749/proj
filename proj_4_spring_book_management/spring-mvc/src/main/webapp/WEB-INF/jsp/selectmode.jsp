<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>정보기입</title>
</head>
<body>
	<h2>select mode</h2>
	<p2>0. addbook	1. addmember	2. boroowbook	3. reservebook</p2>
	<p2>4. returnbook	5. findbook	6. findmember	7. showbook</p2>
	<p2>8. showmember</p2>
	<form action="/spring-mvc/selectmode" method="post">
		<p>
			<label>mode:<br> <input type="text" name="mode" id="mode">
			</label>
		</p>
		<input type="submit" value="결정">
	</form>
</body>
</html>
