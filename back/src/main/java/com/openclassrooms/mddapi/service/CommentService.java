package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.CommentRequest;
import com.openclassrooms.mddapi.dto.CommentResponse;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentResponse createComment(CommentRequest request, User user) {
        // Mapping manuel
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found")));
        comment.setUser(user); // utilisateur connecté

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
