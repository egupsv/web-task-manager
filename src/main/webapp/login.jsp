<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
    <script type="text/javascript">
        const count = () => {
            document.getElementById("counter").style.display="block";
        }
    </script>
</head>
<body>
<div class="form">
    <h1>Log in</h1><br>
    <form method="post" action="/web_task_manager-1.0-SNAPSHOT/login">
        <jsp:useBean id="TryChecker" class ="com.example.web_task_manager.servlet.TryChecker" scope="page"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfLoginDiv()}">login or password is incorrect</div>
        <input type="text" required placeholder="login" name="login">
        <br />
        <input type="password" required placeholder="password" name="password">
        <br />
        <input class="button" type="submit" onclick="count()" value="Log in">
        <br />
        <input type="submit" value="Sign up"
               onclick="window.location='/web_task_manager-1.0-SNAPSHOT/signup.jsp';" />
    </form>
</div>
</body>
</html>
