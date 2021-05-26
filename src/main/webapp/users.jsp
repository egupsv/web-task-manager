<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users list</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
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
        <ul id="users-ul" class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link active nav-el" aria-current="page" href="">Web Task Manager</a>
            </li>
            <c:choose>
            <c:when test="${sessionScope.login != null}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle nav-el" href="#" id="navbarDropdown" role="button"
                   data-bs-toggle="dropdown"
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
<c:if test="${sessionScope.invalidU != null}">
    <div class="container">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <p><strong>${sessionScope.invalidU}</strong></p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.exportFail != null}">
    <div class="container">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <p><strong>${sessionScope.exportFail}</strong></p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
</c:if>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}/users">
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
                <th scope="col">Export</th>
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
                        <button type="button" class="btn btn-warning" value="Edit" onclick="showEditFields(${user.id},'${user.name}','${user.mail}','${user.role}')">
                            Edit
                        </button>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/tasks/${user.name}" class="btn btn-primary"> Go to
                            Tasks</a>
                    </td>
                    <td>
                        <button class="btn btn-secondary" type="submit" name="export"
                                value="${user.getId()}">export
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <button id="export_button" class="btn btn-primary" type="submit" name="export" value="all">Export all</button>
            <input id="import_button" class="btn btn-primary" type="button" onclick="showUploadField()" value="import"/>
            <button type="button" class="btn btn-primary" onclick="showUserAddFields()">Add user</button>
        </div>
    </form>

    <div class="compose_user_field">
        <form id="edit_form" method="post" style="display: none" action="${pageContext.request.contextPath}/users">
            <input id="new_user_check" name="new_user_check" type="hidden" value="0">
            <div>
                <label for="target_id">target user id:</label>
                <a id="target_id" style="text-align: center">0</a>
            </div>
            <div>
                <label for="name"><b>Set Name</b></label>
                <input id="name" type="text" name="name" required placeholder="Name"/>
            </div>
            <div>
                <label for="mail"><b>Set Mail</b></label>
                <input id="mail" type="text" name="mail" required placeholder="Mail"/>
            </div>
            <div>
                <label for="password"><b>Set Password</b></label>
                <input id="password" type="text" name="password" placeholder="Password"/>
            </div>
            <div>
                <label for="role"><b>Set Role</b></label>
                <input id="role" type="text" name="role" placeholder="role"/>
            </div>

            <%--<input type="submit" value="Confirm"/>--%>
            <button class="user_b" id="submit_edit_b" name="submit_edit_b" type="submit" value="0">Submit</button>
            <button class="user_b" id="close_edit_form" onclick="hideEditFields()">Close</button>
            <button class="user_b delete_b" type="submit" id="delete_b" name="delete" value="0">Delete User</button>
        </form>
        <form id="import_form" method="post" style="display: none"
              action="${pageContext.request.contextPath}/users" enctype="multipart/form-data">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" value="replace">
                <label class="form-check-label" for="flexRadioDefault1">
                    import with users replacement
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" value="add" checked>
                <label class="form-check-label" for="flexRadioDefault2">
                    import with adding tasks to existing users
                </label>
            </div>
            <div class="p-3 mb-2 bg-secondary text-white">
                <p> to import new users, it is necessary that the encrypted password and user email must be specified in the xml-file</p>
                <p>
                    <strong>import with users replacement</strong>  means that if a user with a name from the file
                    already exists, then all his (her) tasks will be overwritten
                </p>
                <p>
                    <strong>import with adding tasks to existing users</strong> means that if a user with a name from
                    the file already exists, then all his (her) tasks will remain, and new ones will be added
                </p>
            </div>
            <div>
                <input id="upload_button" class="btn btn-primary" type="file" name="file" accept="text/xml"/>
            </div>
            <input type="submit" class="btn btn-success" value="confirm"/>
        </form>
    </div>
</div>

</body>
</html>
