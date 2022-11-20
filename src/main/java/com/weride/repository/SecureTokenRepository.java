package com.weride.repository;

import com.weride.model.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SecureTokenRepository extends JpaRepository<SecureToken, Long>{
    SecureToken findByToken(final String token);
    Long removeByToken(String token);
}
