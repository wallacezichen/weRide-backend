package com.weride.model;

import lombok.*;

import javax.persistence.*;

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
