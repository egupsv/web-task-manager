function showEditFields(id) {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("edit_form").style.display = "table";
    }
    document.getElementById("target_id").textContent = "User: " + id;
    document.getElementById("delete_b").value = id
    document.getElementById("submit_edit_b").value = id;
}

function hideEditFields() {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 1) {
        document.getElementById("edit_form").style.display = "none";
    }
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