<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head> <title>borrowbook</title> </head>
<body>
    <h2>기본 정보 입력</h2>
    <form action="/spring-mvc/borrowbookr" method="post">
       <p> <label>ID:<br>
             <input type="text" name="id" id="id">
             </label> </p>
       <p> <label>password:<br>
             <input type="password" name="password" id="password">
             </label> </p>
      <p> <label>책이름:<br>
            <input type="text" name="bname" id="bname">
            </label> </p>
      <input type="submit" value="정보 전송">
    </form>
</body>
</html>
