<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:useBean id="tar_user" scope="request" type="com.example.web_task_manager.model.User"/>
    <title>${tar_user.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
            integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
            integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
            crossorigin="anonymous"></script>
    <script type="text/javascript">
        var myModal = document.getElementById("myModal");
        var myInput = document.getElementById("myInput");

        myModal.addEventListener("shown.bs.modal", function () {
            myInput.click();
        })
    </script>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="background-color: #5b1375 !important;">
    <div class="container header">
        Web Task Manager
        <a class="btn btn-close-white" href="${pageContext.request.contextPath}/tasks/${sessionScope.login}">Go to
            Tasks</a>
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
<div class="container ">
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

    <!-- Button trigger modal -->
    <button id="myInput" type="button" class="btn btn-outline-light" data-toggle="modal" data-target="#myModal"
            style="max-width: 180px;">
        Change password
    </button>

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Enter new password</h4>
                </div>
                <form id="change_password" method="post">
                    <div class="modal-body">
                        <label>Enter new password:
                            <input name="chg_pass" id="chg_pass" value=""/>
                        </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>


</body>
</html>
