<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:useBean id="tar_user" scope="request" type="com.example.web_task_manager.model.User"/>
    <title>${tar_user.name}</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div>
    <div>
        <div>
            <p id="id_f">ID: ${tar_user.id}</p>
        </div>
        <div>
            <p id="name_f">LOGIN: ${tar_user.name}</p>
        </div>
        <div>
            <p id="role_f">ROLE: ${tar_user.role}</p>
        </div>
    </div>
    <form id="delete_user" method="post">
        <input id="delete" type="hidden" name="delete" value="1"/>
        <button id="del_user_btn"
                type="submit"
                value="1">Delete Account
        </button>
    </form>
</div>
</body>
</html>
