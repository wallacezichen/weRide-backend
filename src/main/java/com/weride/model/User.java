package com.weride.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false)
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
    private Date createDate;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "last_update_at_date")
    private Date lastUpdateAtDate;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_time")
    private Date verificationCodeTime;
}
