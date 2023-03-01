package com.gozip.webchat.webmodel;



import lombok.Getter;

@Getter
public class ChatRoom {

    private String roomId;
    private String name;

    public static ChatRoom create(String roomId, String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = roomId;
        chatRoom.name = name;
        return chatRoom;
    }
}
