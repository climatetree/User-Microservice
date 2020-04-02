package com.climatetree.user.config;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.climatetree.user.dao.RoleDao;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component

public class JwtTokenUtil implements Serializable {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("climatetree")
	private String secret;
	@Autowired
	RoleDao roleDao;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) throws UnsupportedEncodingException {
		return getClaimFromToken(token, Claims::getSubject);

	}

	// retrieve username from jwt token
	public Role getRoleFromToken(String token) throws UnsupportedEncodingException {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token);
			Claims s = claims.getBody();
			return roleDao.findByRoleId(Integer.parseInt(s.get("role").toString()));
		} catch (ExpiredJwtException e) {
			return null;
		}

	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) throws UnsupportedEncodingException {
		Date expDate = getClaimFromToken(token, Claims::getExpiration);
		if (expDate == null)
			return null;
		return expDate;
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
			throws UnsupportedEncodingException {
		final Claims claims = getAllClaimsFromToken(token);
		if (claims == null)
			return null;
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) throws UnsupportedEncodingException {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token);
			// System.out.println("JWT ---- line 55"+x.get(Constants.ROLE.getStatusCode()));
			Claims s = claims.getBody();

//			System.out.println("Role of priyanka ---"+ roleDao.findByRoleId(Integer.parseInt(s.get("role").toString())).getName() );
			return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return null;
		}
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) throws UnsupportedEncodingException {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(User userDetails) throws UnsupportedEncodingException {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.EMAIL.getStatusCode(), userDetails.getEmail());
		claims.put(Constants.USERID.getStatusCode(), userDetails.getUserId());
		claims.put(Constants.ROLE.getStatusCode(),
				userDetails.getRole() != null ? userDetails.getRole().getRoleId() : null);
		claims.put(Constants.NICKNAME.getStatusCode(), userDetails.getNickname());
		return doGenerateToken(claims, userDetails.getNickname());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) throws UnsupportedEncodingException {
		long issueTime = new Date().getTime();
		long expTime = issueTime + JWT_TOKEN_VALIDITY;
		return Jwts.builder().setSubject(subject).setIssuedAt(Date.from(Instant.ofEpochSecond(issueTime)))
				.setExpiration(Date.from(Instant.ofEpochSecond(expTime))).addClaims(claims)
				.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();

	}

	// validate token
	public Boolean validateToken(String token, User userDetails) throws UnsupportedEncodingException {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getNickname());
	}
}