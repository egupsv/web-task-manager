<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User</title> <%-- ${tar_user.name} --%>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div>
    <form method="get">
        <jsp:useBean id="tar_user" scope="session" type="com.example.web_task_manager.model.User"/>
        <c:choose>
            <c:when test="${empty tar_user}">
                <a>users is empty</a>
            </c:when>
            <c:otherwise>
                <a>user_Name ="${tar_user.name}"></a>
                <a>id = "${tar_user.id}"></a>
                <a>user_Name = ${tar_user.name}"></a>
            </c:otherwise>
        </c:choose>

    </form>
</div>
</body>
</html>
