<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>sign up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light nav-h" style="background-color: #5b1375 !important;">
    <div class="container header">
        Web Task Manager
        <div>
            <c:choose>
                <c:when test="${sessionScope.login != null}">

                    <form action="${pageContext.request.contextPath}/tasks" style="margin-bottom:0 !important;">
                            ${sessionScope.login}
                        <button class="btn btn-outline-danger nav-button" type="submit" name="Logout" value="Logout">Log Out
                        </button>
                    </form>
                </c:when>

                <c:otherwise>
                    please log in
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div class="container auth-center">
    <h1 class="auth-title">Sign up</h1>
    <form method="post" action="${pageContext.request.contextPath}/signup">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker" scope="page"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfSignupDiv()}">this login is already used</div>
        <div class="container">
            <label for="mail"><b>Email</b></label>
            <input id="mail" class="auth_field" type="email" required placeholder="Enter Email" name="mail"
                   pattern="(?:[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+)*|&quot;(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*&quot;)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"/>

            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$" title="Username must be more than 4 symbols and less than 16">

            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>

            <input class="auth_button" type="submit" value="Sign up">
            <input class="auth_button" type="button" value="Log in"
                   onclick="window.location='/web_task_manager-1.0-SNAPSHOT/login.jsp';"/>
        </div>
    </form>
</div>
</body>
</html>
