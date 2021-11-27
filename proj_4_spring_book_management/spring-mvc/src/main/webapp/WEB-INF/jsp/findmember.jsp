<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head> <title>findmember</title> </head>
<body>
    <h2>기본 정보 입력</h2>
    <form action="/spring-mvc/findmemberr" method="post">
       <p> <label>id:<br>
             <input type="text" name="id" id="id">
             </label> </p>
      <input type="submit" value="정보 전송">
    </form>
</body>
</html>
