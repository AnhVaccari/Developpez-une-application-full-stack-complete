package com.openclassrooms.mddapi.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.dto.UserResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse register(RegisterRequest request) {

        // 1. Vérifier que l'email n'existe pas déjà
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 2. Convertir DTO → Entity avec ModelMapper
        User user = modelMapper.map(request, User.class);

        // 3. Sauvegarder en base
        User savedUser = userRepository.save(user);

        // 4. Convertir Entity → DTO Response avec ModelMapper
        return modelMapper.map(savedUser, UserResponse.class);
    }



}
