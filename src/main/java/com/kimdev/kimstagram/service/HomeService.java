package com.kimdev.kimstagram.service;

import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.Repository.*;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Follow;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.PostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

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
    public ArrayList<Account> find(String username) {
        ArrayList<Account> accounts = accountRepository.findAllByUsernameLike("%" + username + "%");

        return accounts;
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

    @Transactional
    public int unfollow(followDTO followdto) {
        Account fromAccount = accountRepository.findById(followdto.getFromaccountId()).get();
        Account toAccount = accountRepository.findById(followdto.getToaccountId()).get();

        followRepository.deleteByToaccountAndFromaccount(toAccount, fromAccount);

        fromAccount.setFollowing(fromAccount.getFollowing() - 1);
        toAccount.setFollower(toAccount.getFollower() - 1);

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

    @Transactional
    public ArrayList<Post> getFollowPosts(int principalId) {
        Account principal = accountRepository.findById(principalId).get();

        ArrayList<Follow> followings = followRepository.findByFromaccount(principal);

        ArrayList<Post> posts = new ArrayList<Post>();
        for (int i=0; i<followings.size(); i++) {
            Account following = followings.get(i).getToaccount();

            ArrayList<Post> tmpPosts = postRepository.findAllByAccount(following);
            if (!tmpPosts.isEmpty()) {
                posts.addAll(tmpPosts);
            }
        }

        if (!posts.isEmpty()) {
            posts.sort(Comparator.comparing(Post::getCreateDate).reversed());
        }

        return posts;
    }

    @Transactional(readOnly = true)
    public int getFollowings(int principalId) {
        Account fromAccount = accountRepository.findById(principalId).get();
        ArrayList<Follow> follows = followRepository.findByFromaccount(fromAccount);

        return follows.size();
    }

    @Transactional(readOnly = true)
    public ArrayList<Account> viewLikedUser(int postId) {
        Post post = postRepository.findById(postId).get();
        ArrayList<PostLike> postLikes = postLikeRepository.findAllByPost(post);

        ArrayList<Account> accounts = new ArrayList<Account>();
        for (int i=0; i<postLikes.size(); i++) {
            accounts.add(postLikes.get(i).account);
        }

        return accounts;
    }

    @Transactional
    public int setUsername(String oriusername, String newusername) {
        if (accountRepository.findByUsername(newusername) == null) {
            Account principal = accountRepository.findByUsername(oriusername);
            principal.setUsername(newusername);

            return 1;
        } else { // 이미 존재하는 경우
            return 0;
        }
    }

}
