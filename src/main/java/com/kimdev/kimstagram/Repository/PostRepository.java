package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    ArrayList<Post> findAllByAccount(Account principal);
}
