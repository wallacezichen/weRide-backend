package com.weride.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final long TIME = 1000L * 60 * 60 * 24 * 30; // 30 days

  private static final String SIGNATURE = "WeRide123123918729";

  public static String encode(Map<String, Object> claims) {
    JwtBuilder builder = Jwts.builder();

    return builder.setHeaderParam("typ", "JWT")
        .setHeaderParam("alg", "HS256")
        .setClaims(claims)
        .setSubject("weRide-account-jwt")
        .setExpiration(new Date(System.currentTimeMillis() + TIME))
        .setId(UUID.randomUUID()
            .toString())
        .signWith(SignatureAlgorithm.HS256, SIGNATURE)
        .compact();
  }

  public static Claims decode(final String token) {
    return Jwts.parser()
        .setSigningKey(SIGNATURE)
        .parseClaimsJws(token)
        .getBody();
  }
}
