package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.CommentRequest;
import com.openclassrooms.mddapi.dto.CommentResponse;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentResponse createComment(CommentRequest request) {
        // Mapping manuel
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUserId(request.getUserId());
        comment.setPostId(request.getPostId());

        // Sauvegarder
        Comment savedComment = commentRepository.save(comment);

        // ModelMapper pour la réponse
        return modelMapper.map(savedComment, CommentResponse.class);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

}
