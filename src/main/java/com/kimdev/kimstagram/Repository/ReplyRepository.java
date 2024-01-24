package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    ArrayList<Reply> findAllByPost(Post post);
    ArrayList<Reply> findAllByAccount(Account account);
}
