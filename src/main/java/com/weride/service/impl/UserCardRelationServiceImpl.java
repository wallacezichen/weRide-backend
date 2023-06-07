package com.weride.service.impl;

import com.weride.dto.Result;
import com.weride.model.Card;
import com.weride.model.User;
import com.weride.model.UserCardRelation;
import com.weride.repository.UserCardRelationRepository;
import com.weride.service.UserCardRelationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserCardRelationServiceImpl implements UserCardRelationService {
    private final UserCardRelationRepository userCardRelationRepository;
    private final Result result;

    public UserCardRelationServiceImpl(UserCardRelationRepository userCardRelationRepository, Result result) {
        this.userCardRelationRepository = userCardRelationRepository;
        this.result = result;
    }

    @Override
    @Transactional
    public Result addCardToUser(UserCardRelation userCardRelation) {
        if (relationExists(userCardRelation)) {
            return Result.fail("Relation already exists");
        }

        userCardRelationRepository.save(userCardRelation);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result removeCardFromUser(UserCardRelation userCardRelation) {
        if (!relationExists(userCardRelation)) {
            return Result.fail("Relation does not exist");
        }

        userCardRelationRepository.delete(userCardRelation);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result updateCard(UserCardRelation userCardRelation, Card card) {
        if (!relationExists(userCardRelation)) {
            return Result.fail("Relation does not exist");
        }

        userCardRelation.setCard(card);
        userCardRelationRepository.save(userCardRelation);
        return Result.ok();
    }

    @Override
    public List<Card> getCardByUserId(Long id) {
        // Implement logic to retrieve cards by user ID
        List<Card> cards = new ArrayList<>();
        for(UserCardRelation relation : userCardRelationRepository.findAll()) {
            if(Objects.equals(relation.getUser().getId(), id)) {
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
