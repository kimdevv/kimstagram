package com.kimdev.kimstagram.service;

import com.kimdev.kimstagram.DTO.DMMessageDTO;
import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.Repository.*;
import com.kimdev.kimstagram.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DMService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DMRoomRepository dmRoomRepository;

    @Autowired
    DMMessageRepository dmMessageRepository;

    private final Map<Integer, Set<WebSocketSession>> sessionMap = new ConcurrentHashMap<>();

    public Set<WebSocketSession> getSessions(int roomId) { // 해당 DMRoom에 연결돼 있는 세션을 찾는다
        return sessionMap.computeIfAbsent(roomId, k -> new HashSet<>());
    }

    public void addSession(int roomId, WebSocketSession session) {
        Set<WebSocketSession> sessions = getSessions(roomId);
        sessions.add(session);
    }

    public void removeSession(int roomId, WebSocketSession session) {
        Set<WebSocketSession> sessions = getSessions(roomId);
        sessions.remove(session);
    }

    @Transactional
    public DMRoom findDmRoom(Account principal, Account user) {
        // 방이 있을 경우
        DMRoom room = dmRoomRepository.findByUser1AndUser2(principal, user);
        if (room == null) {
            room = dmRoomRepository.findByUser1AndUser2(user, principal);
        }

        // 방이 없을 경우에는 방을 새로 만든다.
        if (room == null) {
            room = new DMRoom();

            room.setUser1(principal);
            room.setUser2(user);

            dmRoomRepository.save(room);
        }

        return room;
    }

    @Transactional
    public ArrayList<DMMessage> findDmMessages(DMRoom room) {
        ArrayList<DMMessage> messages = dmMessageRepository.findAllByRoomId(room.getId());
        if (messages == null) {
            messages = new ArrayList<>();
        }

        return messages;
    }

    @Transactional
    public int whoisme(Account principal, DMRoom room) {
        if (room.getUser1() == principal) {
            return 0;
        } else {
            return 1;
        }
    }

    @Transactional
    public void saveDmMessage(DMMessageDTO dmMessageDTO) {
        DMMessage dmMessage = new DMMessage();
        dmMessage.setMessageType(dmMessageDTO.getMessageType());

        int roomId = dmMessageDTO.getRoomId();
        dmMessage.setRoomId(roomId);

        dmMessage.setWho(dmMessageDTO.getWho());
        dmMessage.setMessage(dmMessageDTO.getMessage());

        dmMessageRepository.save(dmMessage);
    }

}
