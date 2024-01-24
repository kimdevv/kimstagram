package com.kimdev.kimstagram.controller.api;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.service.HomeService;
import com.kimdev.kimstagram.service.ProfileService;
import com.kimdev.kimstagram.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class HomeApiController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    SecurityService securityService;

    @Autowired
    HomeService homeService;

    @GetMapping("/checkToken")
    public String intoIndex(@RequestHeader("Authorization") String token, Model model) {
        return "Token_Success";
    }

    @GetMapping("/getPrincipal")
    public Object getPrincipal(@RequestHeader("Authorization") String token) {
        Account result = securityService.getPrincipal(token);

        return result;
    }

    @GetMapping("/getFollowInfo")
    public int getFollowInfo(@RequestParam("fromaccountId") int fromaccountId, @RequestParam("toaccountId") int toaccountId) {
        int result = homeService.getFollowInfo(fromaccountId, toaccountId);

        return result;
    }

    @PostMapping("/upload")
    public int upload(@RequestParam("userId") int userId,
                      @RequestParam("comment") String comment,
                      @RequestPart("pictures") List<MultipartFile> pictures) throws IOException {
        int result = homeService.upload(userId, comment, pictures);
        return result;
    }

    @PostMapping("/follow")
    public int follow(@RequestBody followDTO followdto){
        int result = homeService.follow(followdto);
        return result;
    }
}
