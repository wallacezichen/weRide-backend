package com.weride.service;

import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import java.util.List;

public interface UserCardRelationService {
    Boolean addCardToUser(UserCardRelation userCardRelation);
    Boolean removeCardFromUser(UserCardRelation userCardRelation);
    Boolean updateCard(UserCardRelation userCardRelation, Card card);
    List<Card> getCardByUserId(UserCardRelation userCardRelation);
    List<UserCardRelation> getAllUserCardRelation(UserCardRelation userCardRelation);
}
