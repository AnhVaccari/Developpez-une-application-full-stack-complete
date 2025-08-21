package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.dto.CommentRequest;
import com.openclassrooms.mddapi.dto.CommentResponse;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentResponse createComment(CommentRequest request, User user) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found")));
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        // MAPPING MANUEL au lieu de ModelMapper
        CommentResponse response = new CommentResponse();
        response.setId(savedComment.getId());
        response.setContent(savedComment.getContent());
        response.setUserId(savedComment.getUser().getId());
        response.setPostId(savedComment.getPost().getId());
        response.setCreatedAt(savedComment.getCreatedAt());
        response.setUpdatedAt(savedComment.getUpdatedAt());
        response.setAuthorUsername(savedComment.getUser().getUsername());

        return response;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return comments.stream()
                .map(comment -> {
                    CommentResponse response = new CommentResponse();
                    response.setId(comment.getId());
                    response.setContent(comment.getContent());
                    response.setUserId(comment.getUser().getId());
                    response.setPostId(comment.getPost().getId());
                    response.setCreatedAt(comment.getCreatedAt());
                    response.setUpdatedAt(comment.getUpdatedAt());
                    response.setAuthorUsername(comment.getUser().getUsername());
                    return response;
                })
                .collect(Collectors.toList());
    }

}
