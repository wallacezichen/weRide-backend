import com.weride.model.Card;
import com.weride.repository.CardRepository;
import com.weride.Service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public ResponseEntity<String> saveCard(Card card) {
        Card savedCard = cardRepository.save(card);
        if (savedCard != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Card saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save card");
        }
    }

    @Override
    public ResponseEntity<String> getCardById(long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Card found: " + optionalCard.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }
    }

    @Override
    public ResponseEntity<String> deleteCard(long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Card deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }
    }
}
