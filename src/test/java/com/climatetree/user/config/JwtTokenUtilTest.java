package com.climatetree.user.config;

import static com.climatetree.user.config.JwtTokenUtil.JWT_TOKEN_VALIDITY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.climatetree.user.dao.RoleDao;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@SpringBootTest(classes = JwtTokenUtil.class)
@RunWith(SpringRunner.class)
public class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil util;

    private final String secret = "climatetree";
    
    @MockBean
    RoleDao dao;

    @BeforeEach
    void setUp() {
//        util = new JwtTokenUtil();
    }

    @Test
    void getUsernameFromToken() {

    }

    @Test
    void getExpirationDateFromToken() {
    }

    @Test
    void getClaimFromToken() throws UnsupportedEncodingException {
        User user = new User();
        user.setRole(new Role(1, "ADMIN"));
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);

        String token = util.generateToken(user);
    }

    @Test
    void generateToken() throws UnsupportedEncodingException {
        User user = new User();
        user.setRole(new Role(1, "ADMIN"));
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);
        String subject = "test";

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.EMAIL.getStatusCode(), user.getEmail());
        claims.put(Constants.USERID.getStatusCode(), user.getUserId());
        claims.put(Constants.ROLE.getStatusCode(), user.getRole().getRoleId());
        claims.put(Constants.NICKNAME.getStatusCode(), user.getNickname());
        long issueTime = new Date().getTime();
        long expTime = issueTime + JWT_TOKEN_VALIDITY;

        String expected = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(issueTime)))
                .setExpiration(Date.from(Instant.ofEpochSecond(expTime))).addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();

        String token = util.generateToken(user);
//        assertEquals(expected, token);
    }

    @Test
    @ExceptionHandler(UnsupportedEncodingException.class)
    void testGenerateTokenWithException() throws UnsupportedEncodingException {
        Object user = new User();
        String token = util.generateToken((User) user);
    }

    @Test
    void validateToken() throws UnsupportedEncodingException {
        User user = new User();
        user.setRole(new Role(1, "ADMIN"));
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);

        String token = util.generateToken(user);
        assertTrue(util.validateToken(token, user));

        user.setNickname("wrong");
        assertFalse(util.validateToken(token, user));

        user.setNickname("test");
        String subject = "test";
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.EMAIL.getStatusCode(), user.getEmail());
        claims.put(Constants.USERID.getStatusCode(), user.getUserId());
        claims.put(Constants.ROLE.getStatusCode(), user.getRole().getRoleId());
        claims.put(Constants.NICKNAME.getStatusCode(), user.getNickname());

        String expiredToken = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1584060612)))
                .setExpiration(Date.from(Instant.ofEpochSecond(1584064212))).addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
//        assertFalse(util.validateToken(expiredToken, user));
    }
}