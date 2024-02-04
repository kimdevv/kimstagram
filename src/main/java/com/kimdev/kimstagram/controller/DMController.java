package com.kimdev.kimstagram.controller;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.Repository.ReplyRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.DMMessage;
import com.kimdev.kimstagram.model.DMRoom;
import com.kimdev.kimstagram.service.DMService;
import com.kimdev.kimstagram.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class DMController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DMService dmService;

    @GetMapping("/dm")
    public String dm(Model model, @RequestParam int principalId, @RequestParam int userId) {
        Account principal = accountRepository.findById(principalId).get();
        Account user = accountRepository.findById(userId).get();
        model.addAttribute("principal", principal);
        model.addAttribute("user", user);

        DMRoom room = dmService.findDmRoom(principal, user);
        model.addAttribute("room", room);

        ArrayList<DMMessage> messages = dmService.findDmMessages(room);
        model.addAttribute("messages", messages);

        int whoisme = dmService.whoisme(principal, room);
        model.addAttribute("whoisme", whoisme);

        return "home/dm";
    }
}
