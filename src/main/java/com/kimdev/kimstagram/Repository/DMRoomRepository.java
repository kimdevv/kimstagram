package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.DMRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DMRoomRepository extends JpaRepository<DMRoom, Integer> {
    DMRoom findByUser1AndUser2(Account user1, Account user2);
}
