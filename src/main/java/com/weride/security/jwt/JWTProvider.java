package com.weride.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static com.weride.security.jwt.JWTType.ACCESS;
import static com.weride.security.jwt.JWTType.REFRESH;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Component
public class JWTProvider {

  @Value("${jwt.refresh.secret}")
  private String refreshSecretKey;

  @Value("${jwt.access.secret}")
  private String accessSecretKey;

  @Value("${jwt.subject}")
  private String subject;

  @Value("${jwt.issuer}")
  private String issuer;

  private static final int MINUTES_VALIDITY = 30;

  private static final int DAYS_VALIDITY = 30;

  private final UserDetailsService userDetailsService;

  @Autowired
  public JWTProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }


  public boolean isTokenValid(String token, JWTType type) {
    try {
      getDecodedJWT(token, type);
      return true;
    } catch (JWTVerificationException e) {
      log.error("{}", e.getMessage());
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    String email = retrieveClaims(token, ACCESS).get("email").asString();
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  public String generateToken(JWTType type, String email) {
    var now = ZonedDateTime.now().toInstant();

    Instant expiration = type == REFRESH ?
        now.plus(DAYS_VALIDITY, DAYS) :
        now.plus(MINUTES_VALIDITY, MINUTES);

    return JWT.create()
              .withSubject(subject)
              .withIssuer(issuer)
              .withClaim("email", email)
              .withExpiresAt(Date.from(expiration))
              .sign(Algorithm.HMAC256(type == REFRESH ? refreshSecretKey : accessSecretKey));
  }

  public String getToken(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    return getToken(bearer);
  }

  public String getToken(String bearer) {
    return bearer == null || bearer.length() < 7 ? null : bearer.substring(7);
  }

  private DecodedJWT getDecodedJWT(String token, JWTType type) {
    String key = type == REFRESH ? refreshSecretKey : accessSecretKey;
    return JWT.require(Algorithm.HMAC256(key))
              .withSubject(subject)
              .withIssuer(issuer)
              .build()
              .verify(token);
  }

  private Map<String, Claim> retrieveClaims(String token, JWTType type) {
    return getDecodedJWT(token, type).getClaims();
  }

  public ZonedDateTime getExpirationDate(String token, JWTType type) {
    return ZonedDateTime.ofInstant(
        getDecodedJWT(token, type).getExpiresAt().toInstant(),
        ZoneId.systemDefault()
    );
  }
}
