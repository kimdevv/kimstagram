let checkToken = {
    init: function() {
        document.addEventListener('DOMContentLoaded', checkToken.check);
    },

    check: function() {
        $.ajax({
            type: "GET",
            url: "/checkToken",
            headers: {'Authorization':localStorage.getItem('Authorization')},
            contentType: "application/json; charset=utf-8",
            dataType: "html"
        }).done(function(resp) {
            if (resp != "Token_Success") {
                location.href = "/";
            }
        }).fail(function (resp) { // 토큰이 만료된 경우
            localStorage.removeItem('Authorization');
            location.href = "/";
        });
    }
}

checkToken.init();