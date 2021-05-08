<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:useBean id="tar_user" scope="request" type="com.example.web_task_manager.model.User"/>
    <title>${tar_user.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
            integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
            integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="css/styles.css" %>
    </style>
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
<div class="container">
    <div>
        <p id="id_f">ID: ${tar_user.id}</p>
        <p id="name_f">LOGIN: ${tar_user.name}</p>
        <p id="role_f">ROLE: ${tar_user.role}</p>
        <form id="delete_user" method="post">
            <input id="delete" type="hidden" name="delete" value="1"/>
            <button class="btn btn-danger" id="del_user_btn"
                    type="submit"
                    value="1">Delete Account
            </button>
        </form>
    </div>
    <br>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#chPassModal">
        Change password
    </button>

    <!-- Modal -->
    <div class="modal fade" id="chPassModal" tabindex="-1" aria-labelledby="chPassModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="color:black;">
                <div class="modal-header">
                    <h5 class="modal-title" id="chPassModalLabel">Enter new password:</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="change_password" method="post">
                    <div class="modal-body">
                        <label>Enter new password:
                            <input name="chg_pass" id="chg_pass" value=""/>
                        </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


</div>


</body>
</html>
