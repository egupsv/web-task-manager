<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>sign up</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
            integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
            integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
            crossorigin="anonymous"></script>
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
    <div class="container">
        <form method="post" action="${pageContext.request.contextPath}/signup">
            <label for="mail"><b>Email</b></label>
            <input id="mail" class="auth_field" type="email" required placeholder="Enter Email" name="mail"
                   pattern="(?:[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&amp;&apos;*+\=?^_`{|}~-]+)*|&quot;(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*&quot;)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"/>

            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$" title="Username must be more than 4 symbols and less than 16">

            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>
        </form>
        <button class="auth_button">
            <a href="${pageContext.request.contextPath}/login" style="text-decoration: none; color: #d5d5d5 ">Log in</a>
        </button>
    </div>
</div>
<c:if test="${sessionScope.taken != null}">
    <div class="container">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                ${sessionScope.taken}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
</c:if>
</body>
</html>
