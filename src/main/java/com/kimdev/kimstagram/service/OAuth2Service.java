package com.kimdev.kimstagram.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.controller.api.auth.PrincipalDetail;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId(); // facebook
        String providerId = oAuth2User.getAttribute("id"); // Facebook의 고유 id를 가져옴


        Account account = accountRepository.findByProviderId(providerId);
        if(account == null) { // 해당 유저가 없을 경우
            String username = provider+"_"+providerId; // facebook_12345678 양식
            String password = encoder.encode(UUID.randomUUID().toString());
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setName(name);
            account.setUse_profile_img(0);

            account.setProviderId(providerId);
            account.setRole(RoleType.ROLE_USER);

            accountRepository.save(account);
        }



        return new PrincipalDetail(account, oAuth2User.getAttributes());
    }

    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

        Account principal = principalDetail.getAccount();
        int principalId = principal.getId();
        String username = principal.getUsername();

        if (username.startsWith("facebook")) {
            response.sendRedirect("/facebook/facebookSetUsername?oriusername="+username);
        } else {
            String AccessToken = makeAccessToken(principalId, username);
            String RefreshToken = makeRefreshToken(principalId, username);
            response.sendRedirect("/facebook/facebookAuth?acesstoken="+AccessToken+"&refreshtoken="+RefreshToken);
        }

    }

    public String makeAccessToken(int principalId, String username) {
        // Hash 암호 방식으로 JWT토큰 생성 (RSA방식은 아님)
        String jwtToken = JWT.create()
                .withSubject("JWT_TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*10))) // 토큰의 만료 시간 (10분)
                .withClaim("id", principalId) // 비공개 클레임인데 그냥 값 암거나 넣으면 될 듯?
                .withClaim("username", username)
                .sign(Algorithm.HMAC512("kimdevAuth")); // 토큰에 붙일 고유한 Secret 값

        return "Bearer " + jwtToken;
    }

    public String makeRefreshToken(int principalId, String username) {
        Account account = accountRepository.findById(principalId).get();

        // 리프레시 토큰 생성
        String refreshToken = JWT.create()
                .withSubject("JWT_TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*60 * 24))) // 24시간 동안 유효
                .withClaim("refresh", "refresh")
                .sign(Algorithm.HMAC512("kimdevRefresh"));

        securityService.saveRefreshToken(refreshToken, account);

        return "Bearer " + refreshToken;
    }
}
