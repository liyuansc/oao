package com.oao.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Deprecated
public class JwtTokenManage {
    @Value("${oao.security.jwt.secret:Ac8qLchegKykg(32tu21y}")
    private String secret;

    public String createToken(String userName) {
        return this.createToken(userName, null);
    }


    public String createToken(String userName, Date expiration) {
        Claims claims = Jwts
                .claims()
                .setSubject(userName);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret);
        if (expiration != null) {
            builder = builder.setExpiration(expiration);
        }
        return builder.compact();
    }

    public Authentication getAuthentication(String token) {
        /**
         *  parse the payload of token
         */
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        OaoUserDetails principal = new OaoUserDetails(claims.getSubject(), null);
        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
    }
}
