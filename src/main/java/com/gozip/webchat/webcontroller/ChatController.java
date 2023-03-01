package com.gozip.webchat.webcontroller;

import com.gozip.webchat.webmodel.ChatRoom;
import com.gozip.webchat.webservice.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom chatRoom(@RequestParam String post_Id, @RequestParam String user_nickname){
        return chatService.createRoom(post_Id, user_nickname);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
