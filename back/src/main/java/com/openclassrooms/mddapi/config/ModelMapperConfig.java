package com.openclassrooms.mddapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.mddapi.dto.PostResponse;
import com.openclassrooms.mddapi.model.Post;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Configuration pour Post → PostResponse
        mapper.createTypeMap(Post.class, PostResponse.class)
                .addMapping(src -> src.getUser().getUsername(), PostResponse::setAuthorUsername)
                .addMapping(src -> src.getTopic().getName(), PostResponse::setTopicName);

        return mapper;
    }
}
