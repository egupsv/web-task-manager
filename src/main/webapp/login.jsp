<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
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
    <form method="post" action="${pageContext.request.contextPath}/login">
        <jsp:useBean id="TryChecker" class="com.example.web_task_manager.servlet.TryChecker"/>
        <div id="try" style="color:red; display:${TryChecker.getPropertyOfLoginDiv()}">login or password is incorrect
        </div>
        <div class="container">
            <label for="login"><b>Username</b></label>
            <input id="login" class="auth_field" type="text" required placeholder="Enter Username" name="login"
                   pattern="^[A-Za-z0-9]{4,16}$"
                   title="Username must be more than 4 symbols and less than 16 and dont contain any special symbols"/>

            <label for="psw"><b>Password</b></label>
            <input id="psw" class="auth_field" type="password" required placeholder="Enter Password" name="password"/>

            <input class="auth_button" type="submit" value="Log in"/>
            <input class="auth_button" type="button" value="Sign up"
                   onclick="window.location='/web_task_manager-1.0-SNAPSHOT/signup.jsp';"/>
        </div>
    </form>
</div>
</body>
</html>
