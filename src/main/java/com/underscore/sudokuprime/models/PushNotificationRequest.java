package com.underscore.sudokuprime.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PushNotificationRequest {
    private String title;
    private String message; //user id
    private int notificationType = -1; //0 - Join Request
    private String creatorUserId;
    private String topic;
    private String token;
}