function formHide(id) {
    var x = document.getElementById(id);
    x.className = x.className.replace("show", "");
}

function formShow(id) {
    var x = document.getElementById(id);
    x.className = x.className + " show";
}