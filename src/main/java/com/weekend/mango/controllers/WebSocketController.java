package com.weekend.mango.controllers;


import com.weekend.mango.models.Notification;
import com.weekend.mango.services.WebSocketService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/websocket")
public class WebSocketController {

    private final WebSocketService webSocketService;


    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestParam Map<String,String> request){

        String content = request.get("content");



        webSocketService.notifyFrontEnd(content);
    }


}
