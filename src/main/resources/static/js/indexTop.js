function getFollowInfo(toaccountId) {
    var principal;

    $.ajax({
        type: "GET",
        url: "/getPrincipal",
        headers: {'Authorization': localStorage.getItem('Authorization')},
        contentType: "application/json; charset=utf-8",
    }).done(function (resp) {
        principal = resp;

        let data = {
            fromaccountId: principal.id,
            toaccountId: toaccountId
        }

        $.ajax({
            type: "GET",
            url: "/getFollowInfo",
            data: data, // GET 요청은 JSON으로 보내지 않고 그대로 보낸다.
            contentType: "application/json; charset=utf-8",
        }).done(function (resp) {
            if (resp === 1) { // 팔로우 되어있는 경우
                var divElement = document.createElement('div');
                divElement.id = 'index_account_unFollowButton';
                divElement.textContent = '팔로잉';
                divElement.onclick = function() {
                    onclickFollow(toaccountId);
                };

                var followOrUnfollowDiv = document.getElementById('followOrUnfollow' + toaccountId);
                followOrUnfollowDiv.appendChild(divElement);
            } else if (resp === 0) { // 팔로우가 안 되어있는 경우
                var divElement = document.createElement('div');
                divElement.id = 'index_account_followButton';
                divElement.textContent = '팔로우';
                divElement.onclick = function() {
                    onclickFollow(toaccountId);
                };

                var followOrUnfollowDiv = document.getElementById('followOrUnfollow' + toaccountId);
                followOrUnfollowDiv.appendChild(divElement);
            } else if (resp === -1) { // 자기 자신일 경우
                var div = document.getElementById("index_account" + toaccountId);
                div.style.display = "none";
            }
        }).fail(function (resp) {
            console.log(resp);
        });
    });
}

function addDivs(id, username, name, use_profile_img) {

    var accountBox = document.getElementById("index_accountBox");

    var accountElement = document.createElement("div");
    accountElement.id = "index_account" + id;
    accountElement.className = "index_account"

    var profileImg = document.createElement("img");
    profileImg.id = "index_profileImg";
    profileImg.src = use_profile_img === '1'
        ? "/dynamicImage/profile/" + username + "/profile.jpg"
        : "/dynamicImage/profile/default.jpg";
    profileImg.addEventListener("click", function () {
        onclickProfile(username);
    });

    var accountNames = document.createElement("div");
    accountNames.id = "index_account_names";

    var usernameDiv = document.createElement("div");
    usernameDiv.id = "index_account_username"
    usernameDiv.style.fontSize = "14px";
    usernameDiv.style.fontWeight = "bold";
    usernameDiv.style.cursor = "pointer";
    usernameDiv.textContent = username;
    usernameDiv.addEventListener("click", function () {
        onclickProfile(username);
    });

    var nameDiv = document.createElement("div");
    nameDiv.style.fontSize = "14px";
    nameDiv.style.color = "#737373";
    nameDiv.textContent = name;

    var popularityDiv = document.createElement("div");
    popularityDiv.style.fontSize = "12px";
    popularityDiv.style.color = "#737373";
    popularityDiv.textContent = "인기";

    accountNames.appendChild(usernameDiv);
    accountNames.appendChild(nameDiv);
    accountNames.appendChild(popularityDiv);

    var followOrUnfollow = document.createElement("div");
    followOrUnfollow.id = "followOrUnfollow" + id;

    getFollowInfo(id)
    /*
    var followButton = document.createElement("div");
    followButton.id = "index_account_followButton";
    followButton.textContent = "팔로우";
    followButton.addEventListener("click", function () {
        onclickProfile(id);
    })*/

    accountElement.appendChild(profileImg);
    accountElement.appendChild(accountNames);
    accountElement.appendChild(followOrUnfollow);
    //accountElement.appendChild(followButton);

    accountBox.appendChild(accountElement);
}