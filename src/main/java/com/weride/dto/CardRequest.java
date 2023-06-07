package com.weride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.YearMonth;

import static java.lang.annotation.ElementType.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    @NotBlank(message = "Card Number should not be blank")
    @Pattern(regexp = "^\\d{16}$", message = "Card Number should be 16 digits long")
    private String cardNumber;

    @NotBlank(message = "Expiration Date should not be blank")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Expiration Date should be in the format MM/YY")
    @FutureDate(message = "Expiration Date should be in the future")
    private YearMonth expirationDate;


    @NotBlank(message = "Name on card should not be blank")
    @Pattern(regexp = "^(?=.{1,50}$)[a-zA-Z]+(?: [a-zA-Z]+)*$\n", message ="Name on Card length should be less than 50 characters")
    @Size(max = 50, message = "Name on Card length should be less than 50 characters")
    private String nameOnCard;

    @NotBlank(message = "CVV should not be blank")
    @Size(max = 3, min = 3, message = "length should be 3 digits long")
    private String cvv;
}



