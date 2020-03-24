package com.climatetree.user.controller;


import com.climatetree.user.config.JwtTokenUtil;
import com.climatetree.user.model.JwtRequest;
import com.climatetree.user.model.JwtResponse;
import com.climatetree.user.model.User;
import com.climatetree.user.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @RequestMapping(value = "/user/login", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws UnsupportedEncodingException {
    final User userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername(),authenticationRequest.getEmail());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }
}