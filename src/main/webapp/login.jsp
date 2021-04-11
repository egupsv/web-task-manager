<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        <%@include file="css/styles.css"%>
    </style>
    <script type="text/javascript">
        <%@include file="js/source.js"%>
    </script>
</head>
<body>
<%--
<header>
    <div class="header-bg">
        <button style="width: 10%"> A BUTTON</button>
    </div>
</header>
--%>
<div class="center">
    <h1>Log in</h1><br>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfLoginDiv()}">login or password is incorrect
        </div>
        <div class="container">
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$"
                   title="Username must be more than 4 symbols and less than 16 and dont contain any special symbols"/>
            <br/>
            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>
            <br/>
            <input class="auth_button" type="submit" value="Log in"/>
        </div>
        <input class="auth_button" type="button" value="Sign up"
               onclick="window.location='/web_task_manager-1.0-SNAPSHOT/signup.jsp';"/>
    </form>
</div>
</body>
</html>
