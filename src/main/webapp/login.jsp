<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
    <script type="text/javascript">
        const count = () => {
            document.getElementById("counter").style.display = "block";
        }
    </script>
</head>
<body>
<div class="center">
    <h1>Log in</h1><br>
    <form method="post" action="/web_task_manager-1.0-SNAPSHOT/login">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker" scope="page"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfLoginDiv()}">login or password is incorrect
        </div>
        <div class="container">
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"/>
            <br/>
            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>
            <br/>
            <input class="auth_button" type="submit" onclick="count()" value="Log in"/>
        </div>
        <input class="auth_button" type="button" value="Sign up"
               onclick="window.location='/web_task_manager-1.0-SNAPSHOT/signup.jsp';"/>
    </form>
</div>
</body>
</html>
