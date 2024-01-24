package com.kimdev.kimstagram.Security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.controller.api.auth.PrincipalDetail;
import com.kimdev.kimstagram.model.Account;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private AccountRepository accountRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Jwt토큰 검증 -> 정상적인 사용자인지 확인한다
        // 헤더가 있는지 확인
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) { // 비정상적인 토큰인 경우
            chain.doFilter(request, response); // 다시 필터를 타도록 체인에 넘김
            return;
        }

        // Jwt토큰 검증
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", ""); // prefix는 빠지고 토큰 부분만 token에 담김.
        String username = JWT
                .require(Algorithm.HMAC512("kimdevAuth")) // 암호화 방식과 Secret 키
                .build().verify(jwtToken) // 토큰 서명
                .getClaim("username").asString(); // 토큰에서 username을 가져온 후 String으로 캐스팅

        // username이 잘 들어왔다는 것은, 서명이 정상적으로 됐다는 뜻.
        if (username != null) {
            Account accountEntity = accountRepository.findByUsername(username).get();
            PrincipalDetail principalDetail = new PrincipalDetail(accountEntity);

            // 이미 서명된 토큰을 통해 username이 있다는 걸 확인했으니, 그냥 강제로 Authentication 객체를 만들어도 됨.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
