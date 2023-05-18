import com.weride.model.Card;

public interface CardService {
    ResponseEntity<String> getCardById(long id);

    ResponseEntity<String> deleteCard(long id);

    ResponseEntity<String> saveCard(Card card);

}
