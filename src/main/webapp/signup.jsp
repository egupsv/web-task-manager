<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>sign up</title>
</head>
<body>
<form method="post" action="">

  <input type="text" required placeholder="login" name="login">
  <br />
  <input type="password" required placeholder="password" name="password">
  <br />
  <input class="button" type="submit" value="Sign up">
  <br />
  <input type="submit" value="Log in"
         onclick="window.location='/web_task_manager-1.0-SNAPSHOT/login.jsp';" />
</form>
</body>
</html>
