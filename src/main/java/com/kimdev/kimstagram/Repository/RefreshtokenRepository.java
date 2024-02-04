package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface RefreshtokenRepository extends JpaRepository<Refreshtoken, Integer> {
    Refreshtoken findByRefreshToken(String refreshtoken);
    void deleteByAccount(Account account);
}
