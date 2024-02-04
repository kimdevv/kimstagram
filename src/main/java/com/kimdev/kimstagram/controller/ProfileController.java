package com.kimdev.kimstagram.controller;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.Repository.ReplyRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model) {
        Account user = accountRepository.findByUsername(username);
        ArrayList<Post> posts = postRepository.findAllByAccount(user);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "home/profile";
    }

    @GetMapping("/post/{postId}")
    public String write(@PathVariable int postId, Model model) {
        Post post = postRepository.findById(postId).get();
        ArrayList<Reply> replies = replyRepository.findAllByPost(post);

        model.addAttribute("post", post);
        model.addAttribute("replies", replies);

        return "home/post";
    }

    @GetMapping("/write")
    public String write() {
        return "home/write";
    }

    @GetMapping("/accounts/edit")
    public String editProfile() {
        return "home/editProfile";
    }
}
