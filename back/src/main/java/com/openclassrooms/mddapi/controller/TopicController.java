package com.openclassrooms.mddapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.service.TopicService;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<TopicResponse> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

}
