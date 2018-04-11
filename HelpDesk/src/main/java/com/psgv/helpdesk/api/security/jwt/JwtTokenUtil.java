package com.psgv.helpdesk.api.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7573491933104481295L;

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_EXPIRED = "exp";

	@Value("{jwt.secret}")
	private String secret;

	@Value("{jwt.expiration}")
	private Long expiration;

	public String getUserNameFromToken(final String token) {
		String userName;
		try {
			final Claims claims = getClaimsFromToken(token);
			userName = claims.getSubject();
		} catch (Exception e) {
			userName = null;
		}
		return userName;
	}

	public Date getExpirationDateFromToken(final String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private Claims getClaimsFromToken(final String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	private Boolean isTokenExpired(final String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> mapClaims = new HashMap<>();
		
		mapClaims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		mapClaims.put(CLAIM_KEY_CREATED, new Date());
		
		return doGenerateToken(mapClaims);
	}
	
	private String doGenerateToken(final Map<String, Object> mapClaims) {
		final Date createdDate = (Date) mapClaims.get(CLAIM_KEY_CREATED);
		Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
		
		return Jwts.builder()
				.setClaims(mapClaims)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public Boolean canTokenBeRefreshed(final String token) {
		return !isTokenExpired(token);
	}
	
	public String refreshToken(final String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED,new Date());
			refreshedToken = doGenerateToken(claims);
		}catch (Exception e) {
			refreshedToken = null;
		}
		
		return refreshedToken;
	}
	
	public Boolean validateToken(final String token, final UserDetails userDetails) {
		JwtUser user = (JwtUser)userDetails;
		String userName = getUserNameFromToken(token);
		return userName.equals(user.getUsername()) && !isTokenExpired(token);
	}
}
