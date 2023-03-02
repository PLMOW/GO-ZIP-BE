package com.gozip.webchat.webcontroller;

import com.gozip.webchat.chatrepository.ChatRoomRepository;
import com.gozip.webchat.webmodel.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

//    @GetMapping("/room")
//    public String rooms(Model model) {
//        return "/room";
//    }

//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room(){
//        return chatRoomRepository.findAllRoom();
//    }

    @PostMapping("/room/{post_Id}")
    @ResponseBody
    public ChatRoom createRoom(@PathVariable String post_Id){
        log.info("POST. /room/{post_Id}, post_id = {}",post_Id);
        return chatRoomRepository.createChatRoom(post_Id);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId){
        log.info("GET. /room/enter/{roomId}, roomId = {}",roomId);
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId){
        log.info("GET. /room/{roomId}, roomId = {}",roomId);
        return chatRoomRepository.findRoomById(roomId);
    }

}