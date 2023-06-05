package com.weride.controller;

import com.weride.model.Card;
import com.weride.repository.CardRepository;
import com.weride.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/card-service")
public class CardController {
  private final CardRepository cardRepository;
  private final CardService cardService;

  public CardController(CardRepository cardRepository, CardService cardService) {
    this.cardRepository = cardRepository;
    this.cardService = cardService;
  }

  @PostMapping("/add")
  public ResponseEntity<String> add(@RequestBody Card card) {
    return cardService.saveCard(card);
  }

  @PostMapping("/delete")
  public ResponseEntity<String> delete(@RequestBody Long id) {
    return cardService.deleteCard(id);
  }

  @PostMapping("/get")
  public ResponseEntity<String> get(@RequestBody Long id) {
    return cardService.getCardById(id);
  }

  @PostMapping("/update")
  public ResponseEntity<String> update(@RequestBody Card card) {
    return cardService.saveCard(card);
  }

}
