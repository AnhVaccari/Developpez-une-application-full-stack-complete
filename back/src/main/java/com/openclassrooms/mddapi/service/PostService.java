package com.openclassrooms.mddapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.PostRequest;
import com.openclassrooms.mddapi.dto.PostResponse;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    @Autowired
    private ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request) {
        // Convertir DTO → Entity
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUserId(request.getUserId());
        post.setTopicId(request.getTopicId());

        // Sauvegarder
        Post savedPost = postRepository.save(post);

        // Convertir Entity → DTO Response
        return modelMapper.map(savedPost, PostResponse.class);
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
            posts = postRepository.findByTopicIdInOrderByCreatedAtAsc(topicIds);
        } else {
            posts = postRepository.findByTopicIdInOrderByCreatedAtDesc(topicIds);
        }

        // 3. Convertir en DTO
        return posts.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

}
