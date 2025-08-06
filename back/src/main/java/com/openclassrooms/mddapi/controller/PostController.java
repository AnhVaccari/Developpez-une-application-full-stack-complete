package com.openclassrooms.mddapi.controller;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.CommentRequest;
import com.openclassrooms.mddapi.dto.CommentResponse;
import com.openclassrooms.mddapi.dto.PostRequest;
import com.openclassrooms.mddapi.dto.PostResponse;
import com.openclassrooms.mddapi.dto.PostWithCommentsResponse;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest request) {
        try {
            PostResponse response = postService.createPost(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<PostResponse>> getFeed(@PathVariable Long userId,
            @RequestParam(defaultValue = "desc") String sort) {

        List<PostResponse> posts = postService.getFeed(userId, sort);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long postId,
            @Valid @RequestBody CommentRequest request) {
        try {
            // S'assurer que le postId correspond
            request.setPostId(postId);
            CommentResponse response = commentService.createComment(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostWithComments(@PathVariable Long id) {
        try {
            // Récupérer l'article
            Optional<Post> postOpt = postRepository.findById(id);
            if (postOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Post post = postOpt.get();
            PostResponse postResponse = modelMapper.map(post, PostResponse.class);

            // Récupérer les commentaires
            List<CommentResponse> comments = commentService.getCommentsByPostId(id);

            // Créer la réponse complète
            PostWithCommentsResponse response = new PostWithCommentsResponse();
            response.setPost(postResponse);
            response.setComments(comments);
            response.setCommentCount(comments.size());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
