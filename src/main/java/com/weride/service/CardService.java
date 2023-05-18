package com.weride.service;

import com.weride.model.Card;
import org.springframework.http.ResponseEntity;

public interface CardService {

  ResponseEntity<String> getCardById(long id);

  ResponseEntity<String> deleteCard(long id);

  ResponseEntity<String> saveCard(Card card);

}
