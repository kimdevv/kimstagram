package com.kimdev.kimstagram.controller.api;

import com.kimdev.kimstagram.DTO.postLikeDTO;
import com.kimdev.kimstagram.DTO.replySaveDTO;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.model.Reply;
import com.kimdev.kimstagram.service.HomeService;
import com.kimdev.kimstagram.service.ProfileService;
import com.kimdev.kimstagram.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ProfileApiController {

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

    @GetMapping("/post/getLike")
    public int getLike(@RequestParam int accountId, @RequestParam int postId) {
        int result = profileService.getLike(accountId, postId);
        return result;
    }

    @PutMapping("/profile/editProfile")
    public int editProfile(@RequestParam("accountId") int accountId,
                           @RequestParam("comment") String comment,
                           @RequestPart("picture") MultipartFile picture) throws IOException {
        int result = profileService.editProfile(accountId, comment, picture);
        return result;
    }

    @PostMapping("/postReply")
    public ArrayList<Reply> postReply(@RequestBody replySaveDTO replySaveDto) throws IOException {
        ArrayList<Reply> result = profileService.postReply(replySaveDto);
        return result;
    }

    @PostMapping("/postLike")
    public int postLike(@RequestBody postLikeDTO postLikedto) {
        int result = profileService.postLike(postLikedto);
        return result;
    }

    @DeleteMapping("/postUnlike")
    public int postUnlike(@RequestBody postLikeDTO postLikedto) {
        int result = profileService.postUnlike(postLikedto);
        return result;
    }

}
