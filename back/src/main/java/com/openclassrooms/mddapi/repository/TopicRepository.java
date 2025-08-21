package com.openclassrooms.mddapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.model.Topic;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // Rechercher par nom (pour éviter les doublons)
    Optional<Topic> findByName(String name);

}
