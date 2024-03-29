package com.kimdev.kimstagram.controller.api;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.FollowRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Follow;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.service.HomeService;
import com.kimdev.kimstagram.service.ProfileService;
import com.kimdev.kimstagram.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
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
    FollowRepository followRepository;

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
        System.out.println(token);

        Account result = securityService.getPrincipal(token);

        return result;
    }

    @GetMapping("/findUser")
    public ArrayList<Account> find(@RequestParam String username) {
        ArrayList<Account> result = homeService.find(username);

        return result;
    }

    @GetMapping("/getFollowings")
    public int getFollowings(@RequestParam int principalId) {
        int result = homeService.getFollowings(principalId);

        return result;
    }

    @GetMapping("/getFollowInfo")
    public int getFollowInfo(@RequestParam("fromaccountId") int fromaccountId, @RequestParam("toaccountId") int toaccountId) {
        int result = homeService.getFollowInfo(fromaccountId, toaccountId);

        return result;
    }

    @GetMapping("/getFollowPosts")
    public ArrayList<Post> getFollowPosts(@RequestParam int principalId) {
        ArrayList<Post> posts = homeService.getFollowPosts(principalId);

        return posts;
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

    @DeleteMapping("/unfollow")
    public int unfollow(@RequestBody followDTO followdto){
        int result = homeService.unfollow(followdto);
        return result;
    }

    @GetMapping("/viewLikedUser")
    public ArrayList<Account> viewLikedUser(@RequestParam int postId) {
        ArrayList<Account> result = homeService.viewLikedUser(postId);
        return result;
    }

    @GetMapping("/setUsername")
    public int setUsername(@RequestParam String oriusername, @RequestParam String newusername) {
        int result = homeService.setUsername(oriusername, newusername);
        return result;
    }
}
