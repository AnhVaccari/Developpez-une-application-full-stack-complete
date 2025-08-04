package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TopicResponse> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .collect(Collectors.toList());
    }


}
