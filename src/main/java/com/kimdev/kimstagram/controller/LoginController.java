package com.kimdev.kimstagram.controller;

import com.kimdev.kimstagram.controller.api.auth.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Controller // jsp파일을 반환함 -> viewResolver 호출
public class LoginController {
    @GetMapping({"", "/", "login"})
    public String login() {
        return "login"; // login.jsp 호출
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/testrole/tt")
    public String testrole(Authentication authentication) {
        return "home/index";
    }
}
