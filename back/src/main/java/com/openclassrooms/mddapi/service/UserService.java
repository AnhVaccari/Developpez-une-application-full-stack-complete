package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



}
