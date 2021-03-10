<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>


<head>
    <title>sign up</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div class="center">
    <h1>Sign up</h1><br>
    <form method="post" action="/web_task_manager-1.0-SNAPSHOT/signup">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker" scope="page"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfSignupDiv()}">this login is already used</div>
        <div class="container">
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login">
            <br/>
            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>
            <br/>
            <input class="auth_button" type="submit" value="Sign up">
        </div>
        <input class="auth_button" type="button" value="Log in"
               onclick="window.location='/web_task_manager-1.0-SNAPSHOT/login.jsp';"/>
    </form>
</div>
</body>
</html>
