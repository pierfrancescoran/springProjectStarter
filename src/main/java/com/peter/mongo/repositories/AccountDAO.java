/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.mongo.repositories;

import com.peter.model.entities.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author peter
 */
@Repository
public class AccountDAO implements UserDetailsService {

    private final AccountRepository repository;

    public AccountDAO(AccountRepository rep) {
        repository=rep;
    }

        @Override
        public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
            return repository.findByEmail(email);
        }
        
        public Account saveAccount(Account account){
            return repository.save(account);
        }

    }
