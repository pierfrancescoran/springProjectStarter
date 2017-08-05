/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author peter
 */
@Document
public class Account implements UserDetails{
    
    @Id
    private String id;
 
    @JsonProperty
    @Field("is_active")
    private boolean isActive;
    @NotNull
    @Size(min=6, max=15)
    private String password;
    @NotNull
    @Size(min=6, max=20)
    private String email;
    @JsonProperty
    @Past
    @Field("creation_time")
    private LocalDate creationTime;
    @JsonProperty
    @Size(min=1, max=1)
    private Collection<User> users;

    public Account(boolean isActive, String password, String email, LocalDate creationTime, Collection<User> users) {
        id=null;
        this.isActive = isActive;
        this.password = password;
        this.email = email;
        this.creationTime = creationTime;
        this.users = users;
    }

    public Account() {
        //The empty constructor is needed by the Spring framework to generate the entities
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public Collection<User> getUsers() {
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection <GrantedAuthority> a = new ArrayList<>();
        a.add((GrantedAuthority) () -> "user");
        return a;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isIsActive();
    }

    @Override
    public boolean isEnabled() {
        return isIsActive();
    }

    
    
    
}
