package com.kimdev.kimstagram.Repository;


import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.DMMessage;
import com.kimdev.kimstagram.model.DMRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DMMessageRepository extends JpaRepository<DMMessage, Integer> {
    ArrayList<DMMessage> findAllByRoomId(int roomId);
}
