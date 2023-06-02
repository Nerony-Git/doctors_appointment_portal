
function showPassword(targetID, iconID) {
    var x = document.getElementById(targetID);
    var y = document.getElementById(iconID)
    if (x.type === "password"){
        x.type = "text";
        y.classList.remove('bi-eye-slash');
        y.classList.add('bi-eye');
    } else {
        x.type = "password";
        y.classList.remove('bi-eye');
        y.classList.add('bi-eye-slash');
    }
}
