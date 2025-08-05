package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String authorUsername;

}
