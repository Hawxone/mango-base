package com.weekend.mango.models;

import com.weekend.mango.entities.NotificationType;
import com.weekend.mango.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;
    private String content;
    private UserModel userTo;
    private UserModel userFrom;
    private NotificationType notificationType;
    private boolean delivered;
    private boolean read;

}
