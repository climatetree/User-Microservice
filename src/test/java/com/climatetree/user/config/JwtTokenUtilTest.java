package com.climatetree.user.config;

import com.climatetree.user.enums.Constants;
import com.climatetree.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = JwtTokenUtil.class)
@RunWith(SpringRunner.class)
class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil util;

    private final String secret = "climatetree";

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
        user.setRoleId(1);
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);

        String token = util.generateToken(user);
    }

    @Test
    void generateToken() throws UnsupportedEncodingException {
        User user = new User();
        user.setRoleId(1);
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);
        String subject = "test";

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.EMAIL.getStatusCode(), user.getEmail());
        claims.put(Constants.USERID.getStatusCode(), user.getUserId());
        claims.put(Constants.ROLE.getStatusCode(), user.getRoleId());
        claims.put(Constants.NICKNAME.getStatusCode(), user.getNickname());



        String expected = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1584060612)))
                .setExpiration(Date.from(Instant.ofEpochSecond(1584064212))).setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();

        String token = util.generateToken(user);
//        assertEquals(expected, token);
    }

    @Test
    @ExceptionHandler(UnsupportedEncodingException.class)
    void testGenerateTokenWithException() throws UnsupportedEncodingException {
        Object user = new User();
        String token = util.generateToken((User)user);
    }

    @Test
    void validateToken() throws UnsupportedEncodingException {
        User user = new User();
        user.setRoleId(1);
        user.setNickname("test");
        user.setEmail("a@a.com");
        user.setUserId(1L);

        String token = util.generateToken(user);
        assertTrue(util.validateToken(token, user));

    }
}