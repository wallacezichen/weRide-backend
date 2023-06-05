package com.weride.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "last_update_at_date")
    private LocalDateTime lastUpdateAtDate;

    @Column(name = "refresh_token")
    private String refreshToken;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_instructor_id", nullable = true)
//    private Long drive_id


    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_time")
    private LocalDateTime verificationCodeTime;
}
