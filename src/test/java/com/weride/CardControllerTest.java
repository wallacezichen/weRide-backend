import com.fasterxml.jackson.databind.ObjectMapper;
import com.weride.controller.CardController;
import com.weride.model.Card;
import com.weride.service.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {
    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @Test
    public void testAddCard() throws Exception {
        Card card = Card.builder()
                .cardType("Visa")
                .cardNumber(1234567890)
                .cardName("John Doe")
                .expirationDate(YearMonth.of(2023, 12))
                .cardPin(1234)
                .build();

        when(cardService.saveCard(card)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Card saved successfully"));

        ResponseEntity<String> response = cardController.add(card);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Card saved successfully", response.getBody());

        verify(cardService, times(1)).saveCard(card);
        verifyNoMoreInteractions(cardService);
    }

    @Test
    public void testDeleteCard() {
        long cardId = 1L;

        when(cardService.deleteCard(cardId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Card deleted successfully"));

        ResponseEntity<String> response = cardController.delete(cardId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Card deleted successfully", response.getBody());

        verify(cardService, times(1)).deleteCard(cardId);
        verifyNoMoreInteractions(cardService);
    }

    @Test
    public void testGetCardById() {
        long cardId = 1L;

        when(cardService.getCardById(cardId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Card found"));

        ResponseEntity<String> response = cardController.get(cardId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Card found", response.getBody());

        verify(cardService, times(1)).getCardById(cardId);
        verifyNoMoreInteractions(cardService);
    }
}
