function gotoPost(url) {
    location.href = url;
}

function editProfile() {
    location.href = "/accounts/edit"
}

function showOptionPopup() {
    document.getElementById("optionPopupBackground").style.display = 'block';
}

function optionLogout() {
    location.href = "/logout";
}

function optionCancel() {
    document.getElementById("optionPopupBackground").style.display = 'none';
}