package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimdev.kimstagram.Repository.DMMessageRepository;
import com.kimdev.kimstagram.Repository.DMRoomRepository;
import com.kimdev.kimstagram.model.DMMessage;
import com.kimdev.kimstagram.model.DMRoom;
import com.kimdev.kimstagram.service.DMService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper; // JSON 데이터를 파싱해주는 라이브러리 (Jackson)

    @Autowired
    DMRoomRepository dmRoomRepository;

    @Autowired
    DMMessageRepository dmMessageRepository;

    @Autowired
    DMService dmService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 전송받은 message의 JSON 형태 데이터.
        DMMessage dmMessage = objectMapper.readValue(payload, DMMessage.class); // payload를 DMMessage 형태로 파싱해준다.
        dmMessageRepository.save(dmMessage); // DB에 메시지 저장

        int roomId = dmMessage.getRoomId();
        DMRoom dmroom = dmRoomRepository.findById(roomId).get();
        Set<WebSocketSession> sessions = dmService.getSessions(roomId);

        if (dmMessage.getMessageType().equals(DMMessage.MessageType.ENTER)) { // 입장하는 경우
            dmService.addSession(roomId, session);
        } else if (dmMessage.getMessageType().equals(DMMessage.MessageType.QUIT)) { // 퇴장하는 경우
            dmService.removeSession(roomId, session);
        } else { // 일반 메시지인 경우
            sessions.parallelStream().forEach(roomSession -> sendMessage(roomSession, message)); // 메시지 그냥 전달
        }
    }

    public void sendMessage(WebSocketSession roomSession, TextMessage message) {
        try {
            roomSession.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
