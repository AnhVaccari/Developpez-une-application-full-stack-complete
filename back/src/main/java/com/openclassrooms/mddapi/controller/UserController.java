package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionRequest;
import com.openclassrooms.mddapi.dto.SubscriptionResponse;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

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



}


