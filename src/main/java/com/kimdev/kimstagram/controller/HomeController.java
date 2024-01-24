package com.kimdev.kimstagram.controller;

import com.kimdev.kimstagram.DTO.UploadDataDTO;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.Repository.ReplyRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.PostLike;
import com.kimdev.kimstagram.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @GetMapping("/index")
    public String index(Model model) {
        ArrayList<Account> NoFollowingAccounts = accountRepository.findAllByOrderByFollowerDesc();
        model.addAttribute("NoFollowingAccounts", NoFollowingAccounts);

        return "home/index";
    }
}
