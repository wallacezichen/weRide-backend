package com.weride.model;

import java.util.Date;
import java.util.Set;

import com.weride.model.Driver;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "card")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_number", nullable = false)
    private Integer cardNumber;

    @Column(name = "card_name", nullable = false)
    private String cardName;

    @Column(name = "expiration_date", nullable = false)
    private YearMonth expirationDate;    

    @Column(name = "card_pin", nullable = false)
    private Integer cardPin; 
    
    

}