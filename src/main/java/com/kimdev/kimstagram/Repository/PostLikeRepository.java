package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    PostLike findByAccount(Account account);
    ArrayList<PostLike> findAllByAccountAndPost(Account account, Post post);
    ArrayList<PostLike> findAllByPost(Post post);
    void deleteByAccountAndPost(Account account, Post post);
}
