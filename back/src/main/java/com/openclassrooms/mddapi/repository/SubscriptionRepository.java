package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
