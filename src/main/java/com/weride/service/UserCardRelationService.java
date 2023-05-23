package com.weride.service;

import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserCardRelationService {
    ResponseEntity<String> addCardToUser(UserCardRelation userCardRelation);
    ResponseEntity<String> removeCardFromUser(UserCardRelation userCardRelation);
    ResponseEntity<String> updateCard(UserCardRelation userCardRelation, Card card);
    List<Card> getCardByUserId(UserCardRelation userCardRelation);
    List<UserCardRelation> getAllUserCardRelation(UserCardRelation userCardRelation);
}
