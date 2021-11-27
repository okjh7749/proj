<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head> <title>findbook</title> </head>
<body>
    <h2>기본 정보 입력</h2>
    <form action="/spring-mvc/findbookr" method="post">
      <p> <label>책이름:<br>
            <input type="text" name="bname" id="bname">
            </label> </p>
      <input type="submit" value="정보 전송">
    </form>
</body>
</html>
