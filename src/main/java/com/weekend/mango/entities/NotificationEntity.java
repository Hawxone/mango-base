package com.weekend.mango.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_to_id")
    private UserEntity userTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_from_id")
    private UserEntity userFrom;

    private NotificationType notificationType;

    private boolean delivered;
    private boolean read;
}
