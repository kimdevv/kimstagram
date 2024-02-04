package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    Follow findByToaccountAndFromaccount(Account toaccount, Account fromaccount);

    void deleteByToaccountAndFromaccount(Account toaccount, Account fromaccount);

    ArrayList<Follow> findByFromaccount(Account fromaccount);
}
