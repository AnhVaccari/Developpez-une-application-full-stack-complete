package com.openclassrooms.mddapi.dto;

import java.util.List;
import lombok.Data;

@Data
public class PostWithCommentsResponse {

    private PostResponse post;
    private List<CommentResponse> comments;
    private int commentCount;


}
