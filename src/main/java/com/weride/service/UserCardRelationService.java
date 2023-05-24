package com.weride.service;

import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCardRelationService {
    Boolean addCardToUser(UserCardRelation userCardRelation);
    Boolean removeCardFromUser(UserCardRelation userCardRelation);
    Boolean updateCard(UserCardRelation userCardRelation, Card card);
    List<Card> getCardByUserId(UserCardRelation userCardRelation);
    List<UserCardRelation> getAllUserCardRelation(UserCardRelation userCardRelation);
}
