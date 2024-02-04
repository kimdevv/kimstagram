package com.kimdev.kimstagram.DTO;

import com.kimdev.kimstagram.model.DMMessage;
import com.kimdev.kimstagram.model.DMRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMMessageDTO {
    private DMMessage.MessageType messageType;
    private int roomId;
    private int who;
    private String message;
}
