<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:useBean id="tar_user" scope="request" type="com.example.web_task_manager.model.User"/>
    <c:choose>
        <c:when test="${empty tar_user.id}">
            <title>Empty user</title>
        </c:when>
        <c:otherwise>
            <title>${tar_user.name}</title>
        </c:otherwise>
    </c:choose>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div>
    <div>
        <c:choose>
            <c:when test="${empty tar_user.id}">
                <a>user is empty</a>
            </c:when>
            <c:otherwise>
                <a>user_Name ="${tar_user.name}"></a>
                <a>id = "${tar_user.id}"></a>
                <a>user role = ${tar_user.name}"></a>
            </c:otherwise>
        </c:choose>

    </div>
</div>
</body>
</html>
