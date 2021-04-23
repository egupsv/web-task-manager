<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users list</title>
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
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="background-color: #5b1375 !important;">
    <div class="container header">
        Web Task Manager
        <div>
            <c:choose>
                <c:when test="${sessionScope.login != null}">

                    <form action="${pageContext.request.contextPath}/tasks" style="margin-bottom:0 !important;">
                            ${sessionScope.login}
                        <button class="btn btn-outline-danger" type="submit" name="Logout" value="Logout">Log Out
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
<div class="container">
    <h2 class="h1 fw-bold">Users</h2>
    <table class="table table-hover table-dark table-data">
        <thead class="thead-dark">
        <tr>
            <th scope="col">id</th>
            <th scope="col">Role</th>
            <th scope="col">Name</th>
            <th scope="col">Mail</th>
            <th scope="col">Edit</th>
            <th scope="col">Tasks</th>
        </tr>
        </thead>
        <jsp:useBean id="users" scope="request" type="java.util.List"/>
        <c:forEach var="user" items="${users}">
            <tr>
                <th scope="row"><c:out value="${user.id}"/></th>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.mail}"/></td>
                <td>
                    <button class="btn btn-warning" value="Edit" onclick="showEditFields(${user.id})">
                        Edit
                    </button>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/tasks/${user.name}" class="btn btn-primary"> Go to
                        Tasks</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div>

        <button class="btn btn-primary" onclick="showUserAddFields()">Add user</button>
    </div>
    <div class="compose_user_field">
        <form id="edit_form" method="post" style="display: none" action="${pageContext.request.contextPath}/users">
            <input id="new_user_check" name="new_user_check" type="hidden" value="0">
            <div>
                <label for="target_id">target user id:</label>
                <a id="target_id" style="text-align: center">0</a>
            </div>
            <div>
                <label for="name"><b>Set Name</b></label>
                <input id="name" type="text" name="name"/>
            </div>
            <div>
                <label for="mail"><b>Set Mail</b></label>
                <input id="mail" type="text" name="mail"/>
            </div>
            <div>
                <label for="password"><b>Set Password</b></label>
                <input id="password" type="text" name="password"/>
            </div>
            <div>
                <label for="role"><b>Set Role</b></label>
                <input id="role" type="text" name="role"/>
            </div>

            <%--<input type="submit" value="Confirm"/>--%>
            <button class="user_b" id="submit_edit_b" name="submit_edit_b" type="submit" value="0">Submit</button>
            <button class="user_b" id="close_edit_form" onclick="hideEditFields()">Close</button>
            <button class="user_b delete_b" type="submit" id="delete_b" name="delete" value="0">Delete User</button>
        </form>
    </div>
</div>

</body>
</html>
