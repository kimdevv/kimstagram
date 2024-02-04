package com.kimdev.kimstagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class DMMessage {

    public enum MessageType {
        ENTER,
        QUIT,
        CHAT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public MessageType messageType;

    public int roomId;

    // 0이면 user1이, 1이면 user2가 보낸 메시지인 것.
    public int who;

    @Lob
    public String message;

    @CreationTimestamp
    private Timestamp createDate;
}
