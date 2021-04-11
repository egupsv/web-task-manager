const showEditFields = (id) => {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("edit_form").style.display = "table";
        visibility === 1
    }
    document.getElementById("target_id").textContent = "User: " + id;
    document.getElementById("delete_b").value = id
    document.getElementById("submit_edit_b").value = id;
}
const hideEditFields = () => {
    let visibility = document.getElementById("edit_form").style.display === "none" ? 0 : 1;
    if (visibility === 1) {
        document.getElementById("edit_form").style.display = "none";
        visibility === 0
    }
}

const showAddFields = () => {
    let visibility = document.getElementById("add_form").style.display === "none" ? 0 : 1;
    if (visibility === 0) {
        document.getElementById("add_form").style.display = "table";
        document.getElementById("add_button").value = "Cancel adding";
        visibility === 1
    } else {
        document.getElementById("add_form").style.display = "none";
        document.getElementById("add_button").value = "Add task";
        visibility === 0
    }
}

const count = () => {
    document.getElementById("counter").style.display = "block";
}