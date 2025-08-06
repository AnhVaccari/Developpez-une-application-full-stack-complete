package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionRequest;
import com.openclassrooms.mddapi.dto.SubscriptionResponse;
import com.openclassrooms.mddapi.dto.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.UserProfileResponse;
import com.openclassrooms.mddapi.dto.UserResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import com.openclassrooms.mddapi.service.SubscriptionService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionRequest request) {
        System.out.println("=== SUBSCRIBE ENDPOINT CALLED ===");
        System.out.println("UserId: " + request.getUserId() + ", TopicId: " + request.getTopicId());
        try {
            SubscriptionResponse response = subscriptionService.subscribe(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@Valid @RequestBody SubscriptionRequest request) {
        System.out.println("=== UNSUBSCRIBE ENDPOINT CALLED ===");
        System.out.println("UserId: " + request.getUserId() + ", TopicId: " + request.getTopicId());
        try {
            subscriptionService.unsubscribe(request.getUserId(), request.getTopicId());
            return ResponseEntity.ok("Unsubscribed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            UserProfileResponse profile = userService.getUserProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        try {
            UserResponse response = userService.updateProfile(userId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable Long userId) {
        try {
            UserProfileResponse profile = userService.getUserProfile(userId);
            return ResponseEntity.ok(profile.getSubscriptions());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        try {
            // Récupérer l'email depuis le JWT
            String email = authentication.getName();

            // Trouver l'utilisateur par email
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Long userId = userOpt.get().getId();
            UserProfileResponse profile = userService.getUserProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
