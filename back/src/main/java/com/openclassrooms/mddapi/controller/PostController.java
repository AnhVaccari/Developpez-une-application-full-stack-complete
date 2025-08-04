package com.openclassrooms.mddapi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Map<String, List<TopicResponse>>> getAllTopics() {

        List<TopicResponse> topics = topicService.getAllTopics();
        return ResponseEntity.ok(Map.of("topics", topics));
    }



}
