package com.kimdev.kimstagram.controller.api.auth;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    // 로그인 요청이 오면 자동으로 이 함수가 실행됨.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // password는 스프링이 알아서 처리해 주므로 우리는 username만 관리해주면 됨.
        Account principal = accountRepository.findByUsername(username);
        return new PrincipalDetail(principal);
    }
}
