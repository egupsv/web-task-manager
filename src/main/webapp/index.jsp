<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>tasks</title>
    <script type="text/javascript">
        const showAddFields = () => {
            let visibility = document.getElementById("add_form").style.display==="none" ? 0 : 1;
            if (visibility === 0) {
                document.getElementById("add_form").style.display="block";
                document.getElementById("add_button").value="Cancel adding";
                visibility === 1
            } else {
                document.getElementById("add_form").style.display="none";
                document.getElementById("add_button").value="Add task";
                visibility === 0
            }
        }
    </script>
</head>
<body>
<form method="post" action="/web_task_manager-1.0-SNAPSHOT/tasks">
    <table border="1" style="border-collapse: collapse">
        <caption>Tasks</caption>
        <tr>
            <th>name</th>
            <th>description</th>
            <th>date & time</th>
            <th>state</th>
            <th>delete</th>
            <th>complete</th>
        </tr>
        <jsp:useBean id="tasks" scope="request" type="java.util.List"/>
        <c:forEach var="task" items="${tasks}">
        <tr>
            <td><c:out value="${task.getName()}" /></td>
            <td><c:out value="${task.getDescription()}" /></td>
            <td><c:out value="${task.getTimeString()}" /></td>
            <td style="width: 100px"><c:out value="${task.getState()}" /></td>
            <td><button type="submit" name="delete" style="color:red; width:40px; height:25px" value="${task.getId()}">&#215</button></td>
            <td><button type="submit" name="complete" style="color:green; width:40px; height:25px" value="${task.getId()}">&#10003</button></td>
        </tr>
        </c:forEach>

    </table>
</form>
<div><input id="add_button" type="button" onclick="showAddFields()" value="Add task"/></div>
<form id="add_form" method="post" style="display: none" action="/web_task_manager-1.0-SNAPSHOT/tasks">
    <div>
        <input type="text" required placeholder="task name" name="name"/>
    </div>
    <div>
        <input type="text" placeholder="description" name="description"/>
    </div>
    <div>
        <input
            type="text"
            required
            placeholder="date and time"
            pattern="^([0-2][0-9]|3[0-1])[.](0[0-9]|1[0-2])[.][2][0-9]{3}[ ]([0-1][0-9]|2[0-3])[:]([0-5][0-9])$"
            name="time"
        />
        <p>time in format "dd.mm.yyyy hh:mm" for example "01.01.2021 15:00"</p>
    </div>
    <input type="submit" value="confirm">
</form>
</body>
</html>
