package com.kimdev.kimstagram.service;

import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.FollowRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.Repository.ReplyRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Follow;
import com.kimdev.kimstagram.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    FollowRepository followRepository;

    @Transactional
    public int upload(int userId, String comment, List<MultipartFile> pictures) throws IOException {
        Account user = accountRepository.findById(userId).get(); // 유저
        int pic_size = pictures.size();

        Post post = new Post();
        post.setComment(comment);
        post.setLikecount(0);
        post.setAccount(user);
        post.setPic_size(pic_size);

        post = postRepository.save(post);

        for (int i=0; i<pic_size; i++) {
            MultipartFile picture = pictures.get(i);

            // posts 폴더에 postId 폴더를 만든 후 그 안에 사진 저장
            File path = new File("C:/Users/김현욱/Desktop/Java/kimstagram/src/main/webapp/image/posts/" + Integer.toString(post.getId()) + "/");

            // 경로 생성
            if (!path.exists()) {
                path.mkdirs();
            }

            File file = new File(path, i+".jpg");
            picture.transferTo(file);
        }

        return 0;
    }

    @Transactional
    public int follow(followDTO followdto) {
        Account fromAccount = accountRepository.findById(followdto.getFromaccountId()).get();
        Account toAccount = accountRepository.findById(followdto.getToaccountId()).get();

        Follow follow = new Follow();
        follow.setFromaccount(fromAccount);
        follow.setToaccount(toAccount);

        followRepository.save(follow);

        fromAccount.setFollowing(fromAccount.getFollowing() + 1);
        toAccount.setFollower(toAccount.getFollower() + 1);

        return 0;
    }

    @Transactional(readOnly = true)
    public int getFollowInfo(int fromaccountId, int toaccountId) {
        if (fromaccountId == toaccountId) {
            return -1;
        }

        Account fromAccount = accountRepository.findById(fromaccountId).get();
        Account toAccount = accountRepository.findById(toaccountId).get();

        Follow follow = followRepository.findByToaccountAndFromaccount(toAccount, fromAccount);

        int result;
        result = follow != null ? 1 : 0;

        return result;
    }

}
