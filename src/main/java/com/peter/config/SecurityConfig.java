/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.config;

import com.peter.jwt.TokenProvider;
import com.peter.jwt.Http401UnauthorizedEntryPoint;
import com.peter.jwt.JWTConfigurer;
import com.peter.mongo.repositories.AccountDAO;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security Configuration
 *
 * @author peter
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.sportang.mongo.repositories")
@ComponentScan("com.sportang.JWT")
@EnableMongoRepositories("com.sportang.mongo.repositories")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountDAO accountRepository;

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(accountRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                    .antMatchers("/authenticate").permitAll()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requiresChannel().anyRequest().requiresSecure()
                .and().csrf().disable().headers().frameOptions().disable()
                .and()
                .apply(new JWTConfigurer(tokenProvider))
                .and()
                .headers().cacheControl().disable();

    }

}
