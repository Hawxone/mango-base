package com.weekend.mango.services;

import com.weekend.mango.repositories.NotificationEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationEntityRepository notificationEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationEntityRepository notificationEntityRepository, UserEntityRepository userEntityRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationEntityRepository = notificationEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendGlobalNotification() throws Exception {
        try {
            messagingTemplate.convertAndSend("/topic/global-notifications");

        }catch (Exception e){
            throw new Exception("error occured");
        }

    }


}
