/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import javax.validation.constraints.Past;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author peter
 */
public class User {
    @JsonProperty
    @Past
    private LocalDate dob;
    private String gender;
    private String language;
    @JsonProperty
    @Field("is_active")
    private boolean isActive;
    @JsonProperty
    @Field("creation_time")
    private LocalDate creationTime;
    private String nickname;
    private String countryOfOrigin;
        
    public User() {
        // The empty constructor is needed by the Spring framework to generate the entities
    }

    public User(LocalDate dob, String gender, String language, boolean isActive, LocalDate creationTime, String nickname, String countryOfOrigin) {
        this.dob = dob;
        this.gender = gender;
        this.language = language;
        this.isActive = isActive;
        this.creationTime = creationTime;
        this.nickname = nickname;
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public String getNickname() {
        return nickname;
    }

    
}
