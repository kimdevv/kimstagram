package com.kimdev.kimstagram.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Transactional
    public Account getPrincipal (String token) {
        String jwtToken = token.replace("Bearer ", ""); // prefix는 빠지고 토큰 부분만 token에 담김.
        String username = JWT
                .require(Algorithm.HMAC512("kimdevAuth")) // 암호화 방식과 Secret 키
                .build().verify(jwtToken) // 토큰 서명
                .getClaim("username").asString(); // 토큰에서 username을 가져온 후 String으로 캐스팅

        Account principal = accountRepository.findByUsername(username).get();

        return principal;
    }
}
