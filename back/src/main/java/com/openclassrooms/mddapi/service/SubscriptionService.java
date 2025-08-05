package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionRequest;
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

    public SubscriptionResponse subscribe(SubscriptionRequest request) {

        System.out.println("=== SERVICE: DEBUT SUBSCRIBE ===");
        System.out.println(
                "Request: userId=" + request.getUserId() + ", topicId=" + request.getTopicId());

        // Vérifier si déjà abonné
        Optional<Subscription> existing = subscriptionRepository.findByUserIdAndTopicId(
                request.getUserId(), request.getTopicId());


        if (existing.isPresent()) {

            System.out.println("=== ERREUR: Déjà abonné ===");
            throw new RuntimeException("Already subscribed to this topic");
        }

        System.out.println("=== Pas encore abonné, création... ===");



        // Mapping manuel
        System.out.println("=== Création manuelle de l'entity ===");
        Subscription subscription = new Subscription();
        subscription.setUserId(request.getUserId());
        subscription.setTopicId(request.getTopicId());
        System.out.println("Manual subscription: userId=" + subscription.getUserId() + ", topicId="
                + subscription.getTopicId());


        // Sauvegarder
        System.out.println("=== Sauvegarde en base... ===");
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        System.out.println("Saved with ID: " + savedSubscription.getId());

        // ModelMapper pour la réponse
        System.out.println("=== Conversion vers Response ===");
        return modelMapper.map(savedSubscription, SubscriptionResponse.class);
    }

    public void unsubscribe(Long userId, Long topicId) {
        Optional<Subscription> subscription =
                subscriptionRepository.findByUserIdAndTopicId(userId, topicId);
        if (subscription.isEmpty()) {
            throw new RuntimeException("Not subscribed to this topic");
        }

        subscriptionRepository.delete(subscription.get());
    }
}
