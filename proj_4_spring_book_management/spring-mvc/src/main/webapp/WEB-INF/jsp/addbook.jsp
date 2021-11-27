<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head> <title>addbook</title> </head>
<body>
    <h2>기본 정보 입력</h2>
    <form action="/spring-mvc/addbookr" method="post">
       <p> <label>책이름:<br>
             <input type="text" name="bname" id="bname">
             </label> </p>
       <p> <label>출판사:<br>
             <input type="text" name="publisher" id="publisher">
             </label> </p>
      <p> <label>작가:<br>
            <input type="text" name="author" id="author">
            </label> </p>
      <input type="submit" value="정보 전송">
    </form>
</body>
</html>
