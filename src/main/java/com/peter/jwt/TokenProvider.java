package com.peter.jwt;

import com.peter.model.entities.Account;
import com.peter.mongo.repositories.AccountDAO;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class TokenProvider {
    
    private static final String ISS= "www.peter.com";
    
    private static final String NICKNAME = "nickname";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;
    
    @Autowired
    AccountDAO accountRepository;

    @PostConstruct
    public void init() {
        this.secretKey ="*$twm.`Y%jK?eDzBL9dnqU[aongCOOLS`[EDp2qsy#9G9]M6r4019g#Fp]jp<tk";

        // 30 minutes
//        this.tokenValidityInMilliseconds = 1800000; 
        
        // 6 hours
        this.tokenValidityInMilliseconds = 216000000;
        
        //24 days
        this.tokenValidityInMillisecondsForRememberMe =  2147483647;
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.peter.model.entities.User user = (com.peter.model.entities.User) account.getUsers().toArray()[0];
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        
        
        return Jwts.builder()
            .setIssuer(ISS)
            .setSubject(account.getUsername())
            .claim(NICKNAME, user.getNickname())
            .setAudience(authorities)
            .setExpiration(validity)
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) throws Exception {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();      
                
        Account user = (Account) accountRepository.loadUserByUsername(claims.getSubject());
        
        Collection<? extends GrantedAuthority> authorities =
                user.getAuthorities();
//            Arrays.stream(claims.getAudience().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        
        if(!user.isIsActive()){
            throw new Exception("The account is inactive");
        }
                
        return new UsernamePasswordAuthenticationToken(user, "", authorities);

    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(authToken)
            .getBody();
            return claims.getIssuer() != null && claims.getSubject() != null && claims.get(NICKNAME, String.class) != null &&
                    claims.getAudience() != null && claims.getExpiration() !=null && claims.getIssuedAt() != null &&
                    !claims.getIssuer().isEmpty() && !claims.getSubject().isEmpty() && !claims.get(NICKNAME, String.class).isEmpty() &&
                    !claims.getAudience().isEmpty(); 
            
        } catch (SignatureException e) {
            return false;
        }
    }
}
