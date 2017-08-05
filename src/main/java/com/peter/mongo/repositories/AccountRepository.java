/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.mongo.repositories;

import com.peter.model.entities.Account;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author peter
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    public List<Account> findAccountByEmailAndPassword(String email, String password);
    @Query("{ email:{$regex:?0,$options:'i'}}") 
    public Account findByEmail(String email);
}
