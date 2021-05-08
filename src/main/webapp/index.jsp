<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>tasks</title>
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
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link active nav-el" aria-current="page" href="">Web Task Manager</a>
            </li>
            <c:choose>
            <c:when test="${sessionScope.login != null}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle nav-el" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
                   aria-expanded="false">
                        ${sessionScope.login}
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user">Profile</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tasks/${sessionScope.login}">Tasks</a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.role != null &&'ADMIN'.equals(sessionScope.role)}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/users">Users</a></li>
                        </c:when>
                    </c:choose>
                </ul>
            </li>
        </ul>
        <div>
            <form action="${pageContext.request.contextPath}/tasks/${sessionScope.login}"
                  style="margin-bottom:0 !important;">
                <button class="btn btn-outline-danger nav-button" type="submit" name="Logout" value="Logout">Log
                    Out
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
<jsp:useBean id="target_user" scope="request" type="com.example.web_task_manager.model.User"/>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}/tasks/${target_user.name}">
        <c:choose>
            <c:when test="${target_user.name.equals(sessionScope.login)}">
                <h2 class="h1 fw-bold">Your Tasks</h2>
            </c:when>
            <c:otherwise>
                <h2 class="h1 fw-bold">Tasks of " ${target_user.name} "</h2>
            </c:otherwise>
        </c:choose>
        <table class="table table-hover table-dark table-data">
            <thead class="thead-dark">
            <tr>
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">description</th>
                <th scope="col">date & time</th>
                <th scope="col">state</th>
                <th scope="col">delete</th>
                <th scope="col">complete</th>
                <th scope="col">export</th>
            </tr>
            </thead>
            <jsp:useBean id="Utils" class="com.example.web_task_manager.Utils"/>
            <jsp:useBean id="tasks" scope="request" type="java.util.List"/>
            <tbody>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <th scope="row">${task.getId()}</th>
                    <td><c:out value="${task.getName()}"/></td>
                    <td><c:out value="${task.getDescription()}"/></td>
                    <td><c:out value="${Utils.getFormattedTime(task)}"/></td>
                    <td style="width: 100px"><c:out value="${Utils.getState(task)}"/></td>
                    <td>
                        <button class="btn btn-danger" type="submit" name="delete"
                                value="${task.getId()}">&#215
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-success" type="submit" name="complete" style
                                value="${task.getId()}">&#10003
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-secondary" type="submit" name="export"
                                value="${task.getId()}" onclick="window.location.href='web_task_manager-1.0-SNAPSHOT/task.xml'">export
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input id="add_button" class="btn btn-primary" type="button" onclick="showAddFields()" value="Add task"/>
        <button id="export_button" class="btn btn-primary" type="submit" name="export" value="all">Export all</button>
    </form>
    <div>
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
            <input type="submit" class="btn btn-success" value="confirm">
        </form>
    </div>
</div>
</body>
</html>
