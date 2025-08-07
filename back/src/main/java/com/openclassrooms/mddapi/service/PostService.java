package com.openclassrooms.mddapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentResponse;
import com.openclassrooms.mddapi.dto.PostRequest;
import com.openclassrooms.mddapi.dto.PostResponse;
import com.openclassrooms.mddapi.dto.PostWithCommentsResponse;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request, User user) {
        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user); // L’utilisateur connecté
        post.setTopic(topic);

        // Sauvegarder
        Post savedPost = postRepository.save(post);

        // ModelMapper pour la réponse
        Post savedPostWithRelations = postRepository.findByIdWithUserAndTopic(savedPost.getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(savedPostWithRelations, PostResponse.class);
    }

    public List<PostResponse> getFeed(Long userId, String sort) {
        System.out.println("=== GET FEED for userId: " + userId + " ===");

        // 1. Récupérer les sujets auxquels l'utilisateur est abonné
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        System.out.println("Subscriptions found: " + subscriptions.size());

        List<Long> topicIds = subscriptions.stream()
                .map(Subscription::getTopicId)
                .collect(Collectors.toList());
        System.out.println("Topic IDs: " + topicIds);

        if (topicIds.isEmpty()) {
            System.out.println("No subscriptions, returning empty feed");
            return new ArrayList<>(); // Pas d'abonnements = fil vide
        }

        // 2. Récupérer les posts avec tri
        List<Post> posts;
        if ("asc".equalsIgnoreCase(sort)) {
            posts = postRepository.findByTopicIdInWithUserAndTopicOrderByCreatedAtAsc(topicIds);
        } else {
            posts = postRepository.findByTopicIdInWithUserAndTopicOrderByCreatedAtDesc(topicIds);
        }

        // 3. Convertir en DTO
        return posts.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

    public PostWithCommentsResponse getPostWithComments(Long postId) {
        // Récupérer le post avec relations
        Post post = postRepository.findByIdWithUserAndTopic(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Mapper le post
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);

        // Récupérer les commentaires
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);

        // Créer la réponse complète
        PostWithCommentsResponse response = new PostWithCommentsResponse();
        response.setPost(postResponse);
        response.setComments(comments);
        response.setCommentCount(comments.size());

        return response;
    }

}
