<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>tasks</title>
</head>
<body>
    <table>
        <caption>Tasks</caption>
        <tr>
            <th>name</th>
            <th>description</th>
            <th>date & time</th>
            <th>completed</th>
        </tr>
        <jsp:useBean id="tasks" scope="request" type="java.util.List"/>
        <c:forEach var="task" items="${tasks}">
        <tr>
            <td><c:out value="${task.getName()}" /></td>
            <td><c:out value="${task.getDescription()}" /></td>
            <td><c:out value="${task.getTimeString()}" /></td>
            <td><c:out value="${task.getState()}" /></td>
        </tr>
        </c:forEach>

    </table>
</body>
</html>
