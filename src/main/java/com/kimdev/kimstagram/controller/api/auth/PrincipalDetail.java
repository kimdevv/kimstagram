package com.kimdev.kimstagram.controller.api.auth;

import com.kimdev.kimstagram.model.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements UserDetails, OAuth2User {

    private Account account; // 로그인을 시도하는 Account
    private Map<String, Object> attributes; // 페이스북 로그인이라면 이것도 들어옴

    public PrincipalDetail(Account account) {
        this.account = account;
    }

    public PrincipalDetail(Account account, Map<String, Object> attributes) {
        this.account = account;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(account.getRole());
            }
        });

        return collectors;
    };

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
