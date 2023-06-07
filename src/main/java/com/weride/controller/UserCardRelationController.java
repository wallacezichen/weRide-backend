package com.weride.controller;
import com.weride.dto.Result;
import com.weride.model.Card;
import com.weride.model.UserCardRelation;
import com.weride.repository.UserCardRelationRepository;
import com.weride.service.UserCardRelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userCardRelation-service")
public class UserCardRelationController {
    private final UserCardRelationService userCardRelationService;
    private final UserCardRelationRepository userCardRelationRepository;
    public UserCardRelationController(UserCardRelationService userCardRelationService, UserCardRelationRepository userCardRelationRepository) {
        this.userCardRelationService = userCardRelationService;
        this.userCardRelationRepository = userCardRelationRepository;
    }
    @PostMapping("/add")
    public Result add(@RequestBody UserCardRelation userCardRelation){
        return userCardRelationService.addCardToUser(userCardRelation);
    }
    @DeleteMapping("/delete")
    public Result remove(@RequestBody UserCardRelation userCardRelation){
        return userCardRelationService.removeCardFromUser(userCardRelation);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UserCardRelation userCardRelation, Card card){
        return userCardRelationService.updateCard(userCardRelation, card);
    }

//    //有点问题
    @GetMapping("get/user/{userId}")
    public List<Card> getCardByUser(@PathVariable("userId") Long userId) {
        return userCardRelationService.getCardByUserId(userId);
    }
}
