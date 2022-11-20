package com.weride.repository;

import com.weride.exceptions.InvalidTokenException;
import com.weride.exceptions.UnknownIdentitiferException;
import com.weride.exceptions.UserAlreadyExistException;
import com.weride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    void register(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final User user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    User getUserById(final Long id) throws UnknownIdentitiferException.UnkownIdentifierException;
}
