package com.gozip.webchat.webmodel;



import lombok.Getter;

@Getter
public class ChatRoom {

    private String roomId;

    public static ChatRoom create(String roomId){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = roomId;
        return chatRoom;
    }
}
