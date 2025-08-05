package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long topicId;

}
