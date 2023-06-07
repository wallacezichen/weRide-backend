package com.weride.service;

import com.weride.dto.Result;
import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import java.util.List;

public interface UserCardRelationService {
    Result addCardToUser(UserCardRelation userCardRelation);
    Result removeCardFromUser(UserCardRelation userCardRelation);
    Result updateCard(UserCardRelation userCardRelation, Card card);
    List<Card> getCardByUserId(Long id);
    List<UserCardRelation> getAllUserCardRelation(UserCardRelation userCardRelation);
}
