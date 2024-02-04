package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsernameAndPassword(String username, String password);
    Account findByProviderId(String providerId);

    Account findByUsername(String username);

    ArrayList<Account> findAllByOrderByFollowerDesc();
    ArrayList<Account> findAllByUsernameLike(String username);
}
