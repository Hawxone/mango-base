package com.weekend.mango.services;


import com.weekend.mango.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService{

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    public void notifyFrontEnd(String content) {

        Notification notification = new Notification();
        notification.setContent(content);
        messagingTemplate.convertAndSend("/topic/messages",notification);
    }


}
