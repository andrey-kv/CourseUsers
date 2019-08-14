package com.learnspring.courseUsers.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Base64;

@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    public static final String AUTHMETHOD_BASIC = "Basic ";

    @Autowired
    AuthenticationManager authManager;

    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletRequest request) {

        String[] creds = {"", ""};

        String authHeader = request.getHeader("authorization");
        if (authHeader != null && authHeader.startsWith(AUTHMETHOD_BASIC)) {
            String codedCredentials = authHeader.substring(AUTHMETHOD_BASIC.length());
            String loginPassword = new String(Base64.getDecoder().decode(codedCredentials));
            creds = loginPassword.split(":");
        } else {
            throw new AuthenticationCredentialsNotFoundException("Should use Basic auth.");
        }

        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(creds[0], creds[1]);

        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
        return ResponseEntity.ok("OK");
    }
}
