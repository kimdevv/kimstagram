<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
    <%@ include file="../../layout/indexHeader.jsp"%>
    <%@ include file="../../layout/menu.jsp"%>
    <%@ include file="../getPrincipal.jsp"%>

    <style>
        body {
            margin-left: 74px;
            font-family: Arial;
        }

        #recommendForyouLable {
            width: 90%;
            min-width: 600px;
            max-width: 600px;
            font-weight: bold;
            font-size: 16px;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 36px;
        }

        #index_accountBox {
            width: 90%;
            min-width: 600px;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
            display: flex;
            flex-direction: column;
            overflow: auto;
        }

        #Contents {
            width: 100%;
            height: 100%;
            padding: 50px;
        }

        .index_account {
            display: flex;
            flex-direction: row;
            margin-bottom: 16px;
        }

        #index_profileImg {
            border: 1px solid #dbdbdb;
            border-radius: 50%;
            width: 44px;
            height: 44px;
            margin-right: 10px;
            cursor: pointer;
        }

        #index_account_names {
            width: 90%;
            max-width: 500px;
            display: flex;
            flex-direction: column;
            margin-top: -4px;
        }

        #index_account_followButton {
            width: 82px;
            height: 32px;
            margin-top: 11px;
            background-color: #0095f6;
            border-radius: 6px;
            font-size: 14px;
            font-weight: bold;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer
        }
        #index_account_followButton:hover {
            background-color: #1877f2;
        }
        #index_account_followButton:active {
            background-color: #4cb5f9;
        }

        #index_account_unFollowButton {
            width: 82px;
            height: 32px;
            margin-top: 11px;
            color: black;
            background-color: #efefef;
            border-radius: 6px;
            font-size: 14px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer
        }
        #index_account_unFollowButton:hover {
            background-color: #dbdbdb;
        }
        #index_account_unFollowButton:active {
            background-color: #e6e6e6;
            color: #4c4c4c;
        }

        #unfollowPopupBackground {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.65);
            display: none;
        }

        #unfollowPopup {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 100%;
            height: 50%;
            max-width: 400px;
            max-height: 290px;
            border-radius: 12px;
            background-color: white;
        }

        .unfollowPopupButtons {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            cursor: pointer;
        }

        #unfollowPopupInfo {
            height: 184px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        #unfollowPopupInfoImg {
            width: 90px;
            height: 90px;
            border: 1px solid #ededed;
            border-radius: 50%;
            margin-top: 20px;
            margin-bottom: 30px;
        }

    </style>
</head>

<script src="/js/indexTop.js"></script>

<body>
    <div id="Contents">

        <div id="recommendForyouLable">회원님을 위한 추천</div>

        <div id="index_accountBox">
            <c:forEach var="accounts" items="${NoFollowingAccounts}">
                <script>
                    addDivs('${accounts.id}', '${accounts.username}', '${accounts.name}', '${accounts.use_profile_img}');
                </script>
            </c:forEach>
        </div>
    </div>



    <div id="unfollowPopupBackground">
        <div id="unfollowPopup">
            <div id="unfollowPopupInfo">
                <img id="unfollowPopupInfoImg" src="/dynamicImage/profile/default.jpg">
                <span id="unfollowPopupSpan" style="font-size: 14px"></span>
            </div>
            <hr />
            <div id="optionLogout" class="unfollowPopupButtons" style="height: 5%; color: #ed4956; font-weight: bold">
                팔로우 취소
            </div>
            <hr />
            <div id="optionCancel" class="unfollowPopupButtons" style="height: 3%">
                취소
            </div>
        </div>
    </div>
</body>

<script src="/js/indexBottom.js"></script>