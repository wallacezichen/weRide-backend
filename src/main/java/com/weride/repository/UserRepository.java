package com.weride.repository;

import com.weride.exceptions.InvalidTokenException;
import com.weride.exceptions.UnknownIdentitiferException;
import com.weride.exceptions.UserAlreadyExistException;
import com.weride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    void register(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final User user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    User getUserById(final Long id) throws UnknownIdentitiferException.UnkownIdentifierException;
    Optional<User> findByEmail(String email);

    List<User> findByFirstName(String firstName);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByVerificationCodeAndEmail(String verificationCode, String email);
}
