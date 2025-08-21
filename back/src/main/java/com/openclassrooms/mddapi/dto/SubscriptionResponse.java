package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SubscriptionResponse {
    private Long id;
    private Long userId;
    private Long topicId;
    private LocalDateTime createdAt;

    // On pourrait aussi ajouter les infos du Topic
    private String topicName; // Nom du sujet

}
