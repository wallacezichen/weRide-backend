package com.weride.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
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


  @Column(name = "token", nullable = false)
  private String token;

  @Column(name = "gender", nullable = false)
  private Boolean gender;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "last_update_at_date")
  private Date lastUpdateAtDate;

  @Column(name = "accountVerified", nullable = false)
  private Boolean accountVerified;

  @OneToMany(mappedBy = "user")
  private Set<SecureToken> tokens;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_instructor_id", nullable = true)
//    private Long drive_id


  @Column(name = "verification_code")
  private String verificationCode;

  @Column(name = "verification_code_time")
  private Date verificationCodeTime;
}
