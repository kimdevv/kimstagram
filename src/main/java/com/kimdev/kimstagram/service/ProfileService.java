package com.kimdev.kimstagram.service;

import com.kimdev.kimstagram.DTO.postLikeDTO;
import com.kimdev.kimstagram.DTO.replySaveDTO;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.Repository.ReplyRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.PostLike;
import com.kimdev.kimstagram.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ProfileService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Transactional
    public int getLike(int accountId, int postId) {
        Account principal = accountRepository.findById(accountId).get();
        Post post = postRepository.findById(postId).get();

        ArrayList<PostLike> postLikes = postLikeRepository.findAllByAccountAndPost(principal, post);
        return postLikes.size() > 0 ? 1 : 0;
    }

    @Transactional
    public ArrayList<Reply> postReply (replySaveDTO replySaveDto) {
        String comment = replySaveDto.getComment();
        int accountId = replySaveDto.getAccountId();
        int postId = replySaveDto.getPostId();

        Account account = accountRepository.findById(accountId).get();
        Post post = postRepository.findById(postId).get();

        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setAccount(account);
        reply.setPost(post);

        replyRepository.save(reply);

        ArrayList<Reply> result = replyRepository.findAllByPost(post);
        return result;
    }

    @Transactional
    public int postLike(postLikeDTO postLikedto) {
        Account principal = accountRepository.findById(postLikedto.getPrincipalId()).get();
        Post post = postRepository.findById(postLikedto.getPostId()).get();

        int ret_likes = post.getLikecount();
        post.setLikecount(ret_likes + 1);

        PostLike postLike = new PostLike();
        postLike.setAccount(principal);
        postLike.setPost(post);

        postLikeRepository.save(postLike);
        return ret_likes + 1;
    }

    @Transactional
    public int postUnlike(postLikeDTO postLikedto) {
        Account principal = accountRepository.findById(postLikedto.getPrincipalId()).get();
        Post post = postRepository.findById(postLikedto.getPostId()).get();

        int ret_likes = post.getLikecount();
        post.setLikecount(ret_likes - 1);

        postLikeRepository.deleteByAccountAndPost(principal, post);
        return ret_likes - 1;
    }

    @Transactional
    public int editProfile(int accountId, String comment, MultipartFile picture) throws IOException {
        Account principal = accountRepository.findById(accountId).get();

        principal.setComment(comment);
        principal.setUse_profile_img(1);

        // 프로필 이미지 저장
        File path = new File("C:/Users/김현욱/Desktop/Java/kimstagram/src/main/webapp/image/profile/" + principal.getUsername() + "/");

        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File(path, "profile.jpg");
        picture.transferTo(file);

        return 1;
    }
}
