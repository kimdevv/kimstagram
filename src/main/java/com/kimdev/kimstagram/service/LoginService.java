package com.kimdev.kimstagram.service;

import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public int join(Account account) {
        String ori_password = account.getPassword();
        String enc_password = encoder.encode(ori_password);

        account.setPassword(enc_password);
        account.setRole(RoleType.ROLE_USER);
        account.setFollower(0);
        account.setFollowing(0);
        account.setProfile_img("default.jpg");
        account.setComment("");
        accountRepository.save(account);
        return 1;
    }

    @Transactional(readOnly = true)
    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
