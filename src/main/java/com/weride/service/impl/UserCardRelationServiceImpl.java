package com.weride.service.impl;

import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import com.weride.repository.UserCardRelationRepository;
import com.weride.service.UserCardRelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserCardRelationServiceImpl implements UserCardRelationService {
    private final UserCardRelationRepository userCardRelationRepository;

    public UserCardRelationServiceImpl(UserCardRelationRepository userCardRelationRepository) {
        this.userCardRelationRepository = userCardRelationRepository;
    }

    @Override
    public ResponseEntity<String> addCardToUser(UserCardRelation userCardRelation) {
        // Check if the card exists
        //这块逻辑还有点问题需要再讨论一下,我在思考如何query database
        if (relationExists(userCardRelation)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserCardRelation already exists");
        }

        // Implement logic to add the card to the user
        // Replace with your actual implementation
        userCardRelationRepository.save(userCardRelation);
        return ResponseEntity.ok("Card added to the user successfully");
    }

    @Override
    public ResponseEntity<String> removeCardFromUser(UserCardRelation userCardRelation) {
        // Check if the card exists
        if (!relationExists(userCardRelation)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }

        // Implement logic to remove the card from the user
        // Replace with your actual implementation
        userCardRelationRepository.delete(userCardRelation);
        return ResponseEntity.ok("Card removed from the user successfully");
    }

    @Override
    public ResponseEntity<String> updateCard(UserCardRelation userCardRelation, Card card) {
        // Check if the card exists
        if (!relationExists(userCardRelation)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }

        // Implement logic to update the card
        // Replace with your actual implementation
        userCardRelation.setCard(card);
        userCardRelationRepository.save(userCardRelation);
        return ResponseEntity.ok("Card updated successfully");
    }

    @Override
    public List<Card> getCardByUserId(UserCardRelation userCardRelation) {
        // Implement logic to retrieve cards by user ID
        List<Card> cards = new ArrayList<>();
        for(UserCardRelation relation : userCardRelationRepository.findAll()) {
            if(Objects.equals(relation.getUser().getId(), userCardRelation.getUser().getId())) {
                cards.add(relation.getCard());
            }
        }
        return cards;
    }

    @Override
    public List<UserCardRelation> getAllUserCardRelation(UserCardRelation userCardRelation) {
        // Implement logic to retrieve all user-card relations
        // Replace with your actual implementation
        return userCardRelationRepository.findAll();
    }

    private boolean relationExists(UserCardRelation userCardRelation) {
        // Implement logic to check if the card exists
        for(UserCardRelation relation : userCardRelationRepository.findAll()) {
            if(Objects.equals(relation.getUser().getId(), userCardRelation.getUser().getId()) &&
            Objects.equals(relation.getCard().getId(), userCardRelation.getCard().getId())) {
                return true;
            }
        }
        return false;
    }
}