<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users list</title>
    <style>
        <%@include file="css/styles.css"%>
    </style>
    <script type="text/javascript">
        <%@include file="js/source.js"%>
    </script>
</head>
<body>
<div>
    <table>
        <caption class="TableHeader">Users</caption>
        <tr>
            <th>id</th>
            <th>Role</th>
            <th>Name</th>
            <th>Mail</th>
            <th>Edit</th>
            <th>Tasks</th>
        </tr>
        <jsp:useBean id="users" scope="request" type="java.util.List"/>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.mail}"/></td>
                <td>
                    <button value="Edit" onclick="showEditFields(${user.id})">
                        Edit ${user.id}
                    </button>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/tasks/${user.name}">
                        <input type="submit" value="Go to Tasks"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div>

        <button class="button" onclick="showUserAddFields()">Add user</button>
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
