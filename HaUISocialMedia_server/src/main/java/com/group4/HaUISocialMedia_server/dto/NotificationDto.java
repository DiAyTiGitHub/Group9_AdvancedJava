package com.group4.HaUISocialMedia_server.dto;

import com.group4.HaUISocialMedia_server.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private UUID id;
    private Date createDate;
    private String content;
    private NotificationTypeDto notificationType;
    private UserDto owner;
    private UserDto actor;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.createDate = notification.getCreateDate();
        this.content = notification.getContent();
        if (notification.getNotificationType() != null)
            this.notificationType = new NotificationTypeDto(notification.getNotificationType());
        if (notification.getOwner() != null)
            this.owner = new UserDto(notification.getOwner());

        if (notification.getActor() != null)
            this.actor = new UserDto(notification.getActor());

    }
}
