<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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
        <%@include file="css/styles.css"%>
    </style>
    <script type="text/javascript">
        <%@include file="js/source.js"%>
    </script>
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
    <h1 class="auth-title">Log in</h1>
    <div class="container">
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$"
                   title="Username must be more than 4 symbols and less than 16 and dont contain any special symbols"/>

            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>
            <input class="auth_button" type="submit" value="Log in"/>
        </form>
        <button class="auth_button">
            <a href="${pageContext.request.contextPath}/signup" style="text-decoration: none; color: #d5d5d5 ">Sign up</a>
        </button>
    </div>
</div>
<c:if test="${sessionScope.wrong != null}">
    <div class="container">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                ${sessionScope.wrong}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
</c:if>
</body>
</html>
