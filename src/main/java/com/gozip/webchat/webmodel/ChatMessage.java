package com.gozip.webchat.webmodel;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
