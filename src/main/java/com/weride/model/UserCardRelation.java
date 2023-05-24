package com.weride.model;

import java.time.YearMonth;
import javax.persistence.*;

import lombok.*;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "UserCardRelation")
public class UserCardRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    @ManyToOne
    private User user;


}
