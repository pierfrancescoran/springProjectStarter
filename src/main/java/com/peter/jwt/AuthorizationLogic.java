/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.jwt;

import com.peter.model.entities.Account;
import com.peter.model.httpbody.LoginVM;
import com.peter.mongo.repositories.AccountDAO;
import java.util.Collections;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author peter
 */
@EnableMongoRepositories("com.sportang.mongo.repositories")
@Component
public class AuthorizationLogic {
    
    @Autowired
    private AccountDAO accountRepository;
    
    @Inject
    private TokenProvider tokenProvider;
    
    @Inject
    private AuthenticationManager authenticationManager;
    
    public String authorize(LoginVM loginVM) throws Exception{
        Account customer = (Account) accountRepository.loadUserByUsername(loginVM.getUsername());
        if(!customer.isIsActive()){
            throw new Exception("The account is inactive");
        }
        if (customer != null && customer.getPassword().equals(loginVM.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

            try {
                org.springframework.security.core.Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
                return tokenProvider.createToken(authentication, rememberMe);
            } catch (AuthenticationException exception) {
//                new ResponseEntity<>(Collections.singletonMap("AuthenticationException", exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
                throw new Exception("Wrong credentials");
            }
        }
        throw new Exception("Wrong credentials");
    }
    
}
