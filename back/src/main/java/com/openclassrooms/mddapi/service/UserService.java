package com.openclassrooms.mddapi.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.LoginResponse;
import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.dto.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.UserProfileResponse;
import com.openclassrooms.mddapi.dto.UserResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.JwtUtil;
import com.openclassrooms.mddapi.model.Subscription;

import com.openclassrooms.mddapi.model.Topic;

import java.util.List;



@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse register(RegisterRequest request) {

        // Vérifier existence de l'utilisateur
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Convertir DTO → Entity avec ModelMapper
        User user = modelMapper.map(request, User.class);

        // HASHER le mot de passe
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Sauvegarder en base
        User savedUser = userRepository.save(user);

        // Convertir Entity → DTO Response avec ModelMapper
        return modelMapper.map(savedUser, UserResponse.class);
    }


    public LoginResponse login(LoginRequest request) {

        // Trouver l'utilisateur
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {

            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Générer le token JWT
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        // Créer la réponse
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());

        return response;


    }



    public UserProfileResponse getUserProfile(Long userId) {
        // Récupérer l'utilisateur
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        UserProfileResponse profile = modelMapper.map(user, UserProfileResponse.class);

        // Récupérer les abonnements
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        List<Long> topicIds = subscriptions.stream()
                .map(Subscription::getTopicId)
                .collect(Collectors.toList());

        if (!topicIds.isEmpty()) {
            List<Topic> topics = topicRepository.findAllById(topicIds);
            List<TopicResponse> topicResponses = topics.stream()
                    .map(topic -> modelMapper.map(topic, TopicResponse.class))
                    .collect(Collectors.toList());
            profile.setSubscriptions(topicResponses);
        } else {
            profile.setSubscriptions(new ArrayList<>());
        }

        return profile;
    }


    public UserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        // Mettre à jour les champs non null
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }



}
