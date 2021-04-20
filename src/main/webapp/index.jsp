<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>tasks</title>
    <style>
        <%@include file="css/styles.css"%>
    </style>
    <script type="text/javascript">
        <%@include file="js/source.js"%>
    </script>
</head>

<body>
<jsp:useBean id="target_user" scope="request" type="com.example.web_task_manager.model.User"/>
<form method="post" action="${pageContext.request.contextPath}/tasks/${target_user.name}">
    <div class="task_table">
        <table>
            <caption class="TableHeader">Tasks of " ${target_user.name} "</caption>
            <tr>
                <th>name</th>
                <th>description</th>
                <th>date & time</th>
                <th>state</th>
                <th>delete</th>
                <th>complete</th>
                <th>export</th>
            </tr>
            <jsp:useBean id="Utils" class="com.example.web_task_manager.Utils"/>
            <jsp:useBean id="tasks" scope="request" type="java.util.List"/>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td><c:out value="${task.getName()}"/></td>
                    <td><c:out value="${task.getDescription()}"/></td>
                    <td><c:out value="${Utils.getFormattedTime(task)}"/></td>
                    <td style="width: 100px"><c:out value="${Utils.getState(task)}"/></td>
                    <td>
                        <button type="submit" name="delete" style="color:red; width:40px; height:25px"
                                value="${task.getId()}">&#215
                        </button>
                    </td>
                    <td>
                        <button type="submit" name="complete" style="color:green; width:40px; height:25px"
                               value="${task.getId()}">&#10003
                        </button>
                    </td>
                    <td>
                        <button type="submit" name="export" style="width:80px; height:25px"
                               value="${task.getId()}"><a href="${pageContext.request.contextPath}/task.xml" download>export</a>
                        </button>
                    </td>
                </tr>
            </c:forEach>

        </table>
        <input id="add_button" type="button" onclick="showAddFields()" value="Add task"/>
    </div>
</form>

<form id="add_form" method="post" style="display: none"
      action="${pageContext.request.contextPath}/tasks/${target_user.name}">
    <div>
        <label for="name"><b>Task name</b></label>
        <input id="name" type="text" required placeholder="task name" name="name"/>
    </div>
    <div>
        <label for="desc"><b>Task description</b></label>
        <input id="desc" type="text" placeholder="description" name="description"/>
    </div>
    <div>
        <label for="date"><b>Task expire date</b></label>
        <input
                id="date"
                type="text"
                required
                placeholder="date and time"
                pattern="^([0-2][0-9]|3[0-1])[.](0[0-9]|1[0-2])[.][2][0-9]{3}[ ]([0-1][0-9]|2[0-3])[:]([0-5][0-9])$"
                name="time"
                title="time in format &quot;dd.mm.yyyy hh:mm&quot; for example &quot;01.01.2021 15:00&quot;"
                value="${Utils.getCurrentDateString()}"
        />
        <p>time in format "dd.mm.yyyy hh:mm" for example "01.01.2021 15:00"</p>
    </div>
    <input type="submit" value="confirm">
</form>

<form action="${pageContext.request.contextPath}/tasks">
    <button type="submit" name="Logout" value="Logout">Log Out</button>
</form>
</body>
</html>
