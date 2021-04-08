<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<div>
    <table style="border-collapse: collapse">
        <caption class="TableHeader">Tasks</caption>
        <tr>
            <th>id</th>
            <th>Name</th>
            <th>Mail</th>
            <th>Password</th>
            <th>Edit</th>
        </tr>
        <jsp:useBean id="users" scope="request" type="java.util.List"/>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.getId()}"/></td>
                <td><c:out value="${user.getName()}"/></td>
                <td><c:out value="${user.getMail()}"/></td>
                <td><c:out value="${user.getPassword()}"/></td>
                <td>
                    <button name="edit${user.getId()}" style="color:#ff00f2; width:40px; height:25px"
                            value="Edit">
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
