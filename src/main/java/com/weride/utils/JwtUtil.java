package com.weride.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {
	private static long TIME = 1000 * 60 * 60 * 24 * 30; // 30 days
	private static String SIGNATURE = "weRide";

	public static String encode(Map<String, Object> claims) {
		JwtBuilder builder = Jwts.builder();
		String token = builder.setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256").setClaims(claims)
		   .setSubject("weRide-account-jwt").setExpiration(new Date(System.currentTimeMillis() + TIME))
		   .setId(UUID.randomUUID().toString()).signWith(SignatureAlgorithm.HS256, SIGNATURE).compact();

		return token;
	}

	public static Claims decode(String token) {
		return Jwts.parser().setSigningKey(SIGNATURE).parseClaimsJws(token).getBody();
	}
}
