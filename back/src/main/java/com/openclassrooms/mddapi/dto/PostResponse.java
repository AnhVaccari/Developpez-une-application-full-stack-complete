package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long topicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Infos enrichies (pour affichage)
    private String authorUsername;
    private String topicName;



}
