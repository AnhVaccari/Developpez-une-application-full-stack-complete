package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionResponse;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SubscriptionResponse subscribe(Long userId, Long topicId) {

        // Vérifier si déjà abonné
        Optional<Subscription> existing = subscriptionRepository.findByUserIdAndTopicId(userId, topicId);

        if (existing.isPresent()) {
            throw new RuntimeException("Already subscribed to this topic");
        }

        // Mapping manuel
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setTopicId(topicId);

        // Sauvegarder
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // ModelMapper pour la réponse
        return modelMapper.map(savedSubscription, SubscriptionResponse.class);
    }

    public void unsubscribe(Long userId, Long topicId) {

        Optional<Subscription> subscription = subscriptionRepository.findByUserIdAndTopicId(userId, topicId);
        if (subscription.isEmpty()) {
            throw new RuntimeException("Not subscribed to this topic");
        }
        subscriptionRepository.delete(subscription.get());

    }
}
