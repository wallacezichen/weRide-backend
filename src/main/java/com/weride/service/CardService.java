package com.weride.service;

import com.weride.model.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


public interface CardService {

  ResponseEntity<String> getCardById(long id);

  ResponseEntity<String> deleteCard(long id);

  ResponseEntity<String> saveCard(Card card);

}
