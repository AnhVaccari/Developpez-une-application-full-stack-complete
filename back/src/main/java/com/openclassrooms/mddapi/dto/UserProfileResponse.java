package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class UserProfileResponse {
     private Long id;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    
    // Abonnements de l'utilisateur
    private List<TopicResponse> subscriptions;

}
