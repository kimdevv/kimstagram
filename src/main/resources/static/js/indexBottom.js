var principal;

function init() {
    $.ajax({
        type: "GET",
        url: "/getPrincipal",
        headers: {'Authorization': localStorage.getItem('Authorization')},
        contentType: "application/json; charset=utf-8",
    }).done(function (resp) {
        principal = resp;
    });
}

function onclickProfile(username) {
    location.href = "/profile/" + username;
}

function onclickFollow(toaccountId) {
    let data = {
        fromaccountId: principal.id,
        toaccountId: toaccountId
    }

    $.ajax({
        type: "POST",
        url: "/follow",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(function (resp){

        var followOrUnfollowDiv = document.getElementById('followOrUnfollow' + toaccountId);
        followOrUnfollowDiv.removeChild(index_account_followButton);

        var divElement = document.createElement('div');
        divElement.id = 'index_account_unFollowButton';
        divElement.textContent = '팔로잉';
        divElement.onclick = function() {
            onclickFollow(toaccountId);
        };

        var followOrUnfollowDiv = document.getElementById('followOrUnfollow' + toaccountId);
        followOrUnfollowDiv.appendChild(divElement);

    }).fail(function(error){ // 응답의 결과가 실패한 경우. error은 응답받은 데이터가 JSON일 경우 들어가는 것.
        alert(JSON.stringify(error));
    });
}

function onclickUnfollow(toaccountId) {
    var parentDiv = document.getElementById("index_account" + toaccountId);

    document.getElementById(unfollowPopupInfoImg).src = parentDiv.querySelector("#index_profileImg").src;
    document.getElementById(unfollowPopupSpan).innerText = "@" + parentDiv.querySelector('#index_account_username');
}