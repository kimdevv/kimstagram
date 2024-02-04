package com.kimdev.kimstagram.Security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kimdev.kimstagram.Repository.RefreshtokenRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Refreshtoken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtRefreshFilter extends OncePerRequestFilter {

    private RefreshtokenRepository refreshtokenRepository;

    public JwtRefreshFilter(RefreshtokenRepository refreshtokenRepository) {
        this.refreshtokenRepository = refreshtokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try { // Authorization에 담긴 액세스 토큰이 유효할 경우
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) { // Authorization에 담긴 액세스 토큰이 만료됐을 경우
            String refreshToken = request.getHeader("Refresh-Token").replace("Bearer ", "");

            Refreshtoken isValidInDB = refreshtokenRepository.findByRefreshToken(refreshToken);
            if (isValidInDB != null) { // 정상적으로 로그인 한 경우에만
                DecodedJWT decodedRefreshToken = JWT.require(Algorithm.HMAC512("kimdevRefresh"))
                        .build()
                        .verify(refreshToken);

                Date expirationTime = decodedRefreshToken.getExpiresAt();
                Date now = new Date();
                if (expirationTime != null && now.before(expirationTime)) { // 리프레시 토큰이 유효할 경우
                    String reissuedToken = reissueToken(request.getHeader("Authorization").replace("Bearer ", ""), isValidInDB); // 토큰 재 생성
                    response.addHeader("ReissuedToken", "Bearer "+reissuedToken); // 재생성한 토큰을 다시 클라이언트에게 전송.
                } else { // 리프레시 토큰이 만료되었을 경우
                    throw e; // 오류 발생시키기 (로그인창으로)
                }
            } else { // 정상적으로 로그인하지 않은 경우
                throw e; // 오류 발생시키기 (로그인창으로)
            }
        }
    }

    private String reissueToken(String accessToken, Refreshtoken refreshtoken) {
        // 액세스 토큰 재생성
        String reissuedToken = JWT.create()
                .withSubject("JWT_TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*10))) // 토큰의 만료 시간 (10분)
                .withClaim("id", refreshtoken.getAccount().getId()) // 비공개 클레임인데 그냥 값 암거나 넣으면 될 듯?
                .withClaim("username", refreshtoken.getAccount().getUsername())
                .sign(Algorithm.HMAC512("kimdevAuth")); // 토큰에 붙일 고유한 Secret 값

        return reissuedToken;
    }
}
