package com.kimdev.kimstagram.controller.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class LoginApiController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpSession session;

    @PostMapping("/auth/joinProc")
    public int join(@RequestBody Account account) {
        int result = loginService.join(account);
        return result;
    }

}
