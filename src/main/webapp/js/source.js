function showEditFields(id,name,mail,role) {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("edit_form").style.display = "table";
    }
    document.getElementById('new_user_check').value = 0;
    document.getElementById("target_id").textContent = "User: " + id;
    document.getElementById("delete_b").value = id;
    document.getElementById("delete_b").style.display = "inline-block";
    document.getElementById("submit_edit_b").value = id;
    document.getElementById("name").value = name
    document.getElementById("mail").value = mail
    document.getElementById("role").value = role
}

function showUserAddFields() {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("edit_form").style.display = "table";
    }
    document.getElementById("target_id").textContent = "Creating new User.";
    document.getElementById("delete_b").style.display = "none";
    document.getElementById("delete_b").value = 0;
    document.getElementById("submit_edit_b").value = 0;
    document.getElementById('new_user_check').value = 1;
    document.getElementById("name").value = ""
    document.getElementById("mail").value = ""
    document.getElementById("password").value = ""
    document.getElementById("role").value = ""
}

function hideEditFields() {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 1) {
        document.getElementById("edit_form").style.display = "none";
    }
    document.getElementById('new_user_check').value = 0;
    document.getElementById("delete_b").value = 0;
}

function showAddFields() {
    let visibility = document.getElementById("add_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("add_form").style.display = "table";
        document.getElementById("add_button").value = "Cancel adding";
    } else {
        document.getElementById("add_form").style.display = "none";
        document.getElementById("add_button").value = "Add task";
    }
}

const count = () => {
    document.getElementById("counter").style.display = "block";
}

function userDeleteConfirmDialog() {
    if (confirm("Are you sure?")) {
        document.getElementById("delete_input").value = 1;
    } else {
        document.getElementById("delete_input").value = 0;
    }
}

const showUploadField = () => {
    let visibility = document.getElementById("import_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("import_form").style.display = "table";
        document.getElementById("import_button").value = "Cancel import";
    } else {
        document.getElementById("import_form").style.display = "none";
        document.getElementById("import_button").value = "Import";
    }
}

const callAlert = (existed) => {
    window.addEventListener('load',function(){
        alert(existed);
    });
}
