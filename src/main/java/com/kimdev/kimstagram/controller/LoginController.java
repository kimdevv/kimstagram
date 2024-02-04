package com.kimdev.kimstagram.controller;

import com.kimdev.kimstagram.controller.api.auth.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/facebook/facebookSetUsername")
    public String facebookSetUsername(Model model, @RequestParam String oriusername) {
        model.addAttribute("oriusername", oriusername);
        return "facebook/facebookSetUsername";
    }

    @GetMapping("/facebook/facebookAuth")
    public String facebookAuth(Model model, @RequestParam String acesstoken, @RequestParam String refreshtoken) {
        model.addAttribute("Authorization", acesstoken);
        model.addAttribute("refresh", refreshtoken);

        return "facebook/facebookAuth";
    }
}
