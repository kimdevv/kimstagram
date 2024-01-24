package com.kimdev.kimstagram.Security;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Security.filter.ApplyingFilter;
import com.kimdev.kimstagram.Security.filter.JwtAuthenticationFilter;
import com.kimdev.kimstagram.Security.filter.JwtAuthorizationFilter;
import com.kimdev.kimstagram.controller.api.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 먼저 체크하겠다는 것.
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final AccountRepository accountRepository;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf 토큰 비활성화
        http.csrf().disable();

        // 세션을 Stateless로 변경, 기존의 폼 로그인 방식 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable().httpBasic().disable();

        // 권한이 필요한 페이지 설정 및 페이지 리다이렉션 설정
        http.authorizeRequests()
                .antMatchers("/checkToken").access(("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')"))
                .antMatchers("/**", "/index", "/auth/**", "/login", "/join", "/js/**", "/dynamicImage/**", "/image/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(((request, response, authException) -> response.sendRedirect("/")));

        // 필터 구성
        http.addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // WebSecurityConfiguererAdapter가 갖고 있어서 그냥 넘겨주면 된다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), accountRepository));
    }
}
