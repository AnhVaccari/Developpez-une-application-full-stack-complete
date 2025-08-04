package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {
    private Long id;
    private String name;
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
