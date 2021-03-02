<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
</head>
<body>
<div class="form">
    <h1>Log in</h1><br>
    <form method="post" action="/web_task_manager-1.0-SNAPSHOT/login">

        <input type="text" required placeholder="login" name="login">
        <br />
        <input type="password" required placeholder="password" name="password">
        <br />
        <input class="button" type="submit" value="Log in">
        <br />
        <input type="submit" value="Sign up"
               onclick="window.location='/web_task_manager-1.0-SNAPSHOT/signup.jsp';" />
    </form>
</div>
</body>
</html>
