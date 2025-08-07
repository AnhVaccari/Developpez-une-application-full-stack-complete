package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.user JOIN FETCH p.topic WHERE p.topic.id IN :topicIds ORDER BY p.createdAt DESC")
    List<Post> findByTopicIdInWithUserAndTopicOrderByCreatedAtDesc(@Param("topicIds") List<Long> topicIds);

    @Query("SELECT p FROM Post p JOIN FETCH p.user JOIN FETCH p.topic WHERE p.topic.id IN :topicIds ORDER BY p.createdAt ASC")
    List<Post> findByTopicIdInWithUserAndTopicOrderByCreatedAtAsc(@Param("topicIds") List<Long> topicIds);

    @Query("SELECT p FROM Post p JOIN FETCH p.user JOIN FETCH p.topic WHERE p.id = :id")
    Optional<Post> findByIdWithUserAndTopic(@Param("id") Long id);

}
