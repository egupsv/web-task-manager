<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>User</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div class="user_panel">
    <jsp:useBean id="user" scope="request" type="com.example.web_task_manager.model.User"/>
    <form method="post" action="${pageContext.request.contextPath}/user">
        <div id="mail" class="user_line">
            <label for="mail_f">mail:</label>
            <a id="mail_f"><c:out value="${user.getMail()}"/></a>
        </div>
        <div id="pass" class="user_line">
            <label for="chgp"><b>Change password:</b></label>
            <input id="chgp">
            <input name="accept" type="submit" value="Accept">
        </div>
    </form>
</div>
</body>
</html>
