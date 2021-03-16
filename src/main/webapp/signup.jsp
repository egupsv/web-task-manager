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
    <form method="post" action="${pageContext.request.contextPath}/signup">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker" scope="page"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfSignupDiv()}">this login is already used</div>
        <div class="container">
            <label for="mail"><b>Email</b></label>
            <input id="mail" class="auth_field" type="email" required placeholder="Enter Email" name="mail"
                   pattern="(?:[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+)*|&quot;(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*&quot;)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"/>
            <br/>
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$" title="Username must be more than 4 symbols and less than 16">
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
