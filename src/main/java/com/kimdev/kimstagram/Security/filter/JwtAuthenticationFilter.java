package com.kimdev.kimstagram.Security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimdev.kimstagram.controller.api.auth.PrincipalDetail;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private SecurityService securityService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecurityService securityService) {
        this.authenticationManager = authenticationManager;
        this.securityService = securityService;
        setFilterProcessesUrl("/auth/loginProc");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // username, password를 받는다.
        try {
            ObjectMapper om = new ObjectMapper(); // json데이터 파싱하기 위한 클래스

            Account account = om.readValue(request.getInputStream(), Account.class); // request에 담긴 값을 읽어서 Account 객체로 파싱해줌.

            // username과 password로 토큰을 만든다.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());

            // 이 토큰을 가지고 로그인 시도!
            // 이걸 실행할 때 loadUserByUsername()이 실행되는 것!
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 이걸 return해주면 이 객체가 session에 저장됨.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //return super.attemptAuthentication(request, response);
    }

    // attemptAuthentication() 실행 후 인증이 정상적으로 되었다면 아래 함수가 실행된다.
    // 여기서 JWT토큰을 만들어서, request를 요청한 사용자에게 response해주면 됨!
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();

        // Hash 암호 방식으로 JWT토큰 생성 (RSA방식은 아님)
        String jwtToken = JWT.create()
                .withSubject("JWT_TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*10))) // 토큰의 만료 시간 (10분)
                .withClaim("id", principalDetail.getAccount().getId()) // 비공개 클레임인데 그냥 값 암거나 넣으면 될 듯?
                .withClaim("username", principalDetail.getAccount().getUsername())
                .sign(Algorithm.HMAC512("kimdevAuth")); // 토큰에 붙일 고유한 Secret 값

        // 리프레시 토큰 생성
        String refreshToken = JWT.create()
                .withSubject("JWT_TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*60 * 24))) // 24시간 동안 유효
                .withClaim("refresh", "refresh")
                .sign(Algorithm.HMAC512("kimdevRefresh"));


        // 만든 토큰들을 헤더에 붙여서 응답
        response.addHeader("Authorization", "Bearer "+jwtToken);
        response.addHeader("Refresh-Token", "Bearer "+refreshToken);

        securityService.saveRefreshToken(refreshToken, principalDetail.getAccount());
    }
}
