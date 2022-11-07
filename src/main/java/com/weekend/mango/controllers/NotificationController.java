package com.weekend.mango.controllers;

import com.weekend.mango.models.Message;
import com.weekend.mango.models.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class NotificationController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Notification getMessage(Message message) throws Exception {

        Notification notification = new Notification();
        notification.setContent(message.getContent());

        return notification;
    }

}
