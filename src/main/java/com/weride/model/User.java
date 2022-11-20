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

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "last_update_at_date", nullable = false)
    private Date lastUpdateAtDate;

    @Column(name = "accountVerified", nullable = false)
    private Boolean accountVerified;

    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;




//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_instructor_id", nullable = true)
//    private Long drive_id


}