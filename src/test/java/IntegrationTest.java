///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sportang.controller;
//
//import com.sportang.JWT.TokenProvider;
//import com.sportang.bl.ServiceLogic;
//import com.sportang.bl.UserLogic;
//import com.sportang.entities.Account;
//import com.sportang.main.Application;
//import com.sportang.mongo.repositories.AccountDAO;
//import com.sportang.mongo.repositories.AccountRepository;
//import com.sportang.mongo.repositories.CustomQueries;
//import com.sportang.mongo.repositories.GenreRepository;
//import com.sportang.mongo.repositories.MovieRepository;
//import java.security.SecureRandom;
//import java.util.ArrayList;
//import java.util.Collection;
//import javax.inject.Inject;
//import static org.hamcrest.Matchers.hasSize;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
///**
// *
// * @author peter
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//@ComponentScan("com.sportang.config")
//@ComponentScan("com.sportang.JWT")
//@EnableMongoRepositories("com.sportang.mongo.repositories")
//public class IntegrationTest {
//    
//    @Autowired
//    private AccountDAO accountRepository;
//    
//    @Autowired
//    private AccountRepository repository;
//    
//    @Autowired
//    private UserLogic userLogic;
//    
//    @Autowired
//    private ServiceLogic serviceLogic;
//    
//    private MockMvc restUserMockMvc;
//    
//    public IntegrationTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        RestControllers rs = new RestControllers();
//        ReflectionTestUtils.setField(rs, "serviceLogic", serviceLogic);
//        ReflectionTestUtils.setField(rs, "userLogic", userLogic);
//        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(rs).build();
//    }
//
//    @After
//    public void tearDown() {
//    }
//    
//    //               USER DEPENDENT SERVICES:
//    //                  authenticate, get user info, subscribe, add to favorite
//
//    /**
//     * Test of authorize method, of class RestControllers.
//     */
//
//    @Test
//    public void testNonAuthenticateWrongUser() throws Exception {
//        restUserMockMvc.perform(
//                post("/authenticate")
//                        .header("Content-Type", "application/json")
//                        .content("{\"username\":\"wrong@email.com\",\"password\":\"password\",\"rememberMe\":false}".getBytes())
//                        .accept("application/json"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("index"));
//    }
//
//    @Test
//    public void testAuthenticateRightUser() throws Exception {
//        restUserMockMvc.perform(
//                post("/authenticate")
//                        .header("Content-Type", "application/json")
//                        .content("{\"username\":\"black@cat.com\",\"password\":\"password\",\"rememberMe\":false}".getBytes())
//                        .accept("application/json"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("ok"));
//    }
//    
//    
//    /**
//     * Test of getUserInfo method, of class RestControllers.
//     */
//    @Test
//    public void testGetUserInfo() throws Exception {
//
//        System.out.println(((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
//
//        restUserMockMvc.perform(
//                        get("/userInfo")
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                        .andExpect(jsonPath("$.email").exists())
//                        .andExpect(jsonPath("$.email").value("black@cat.com"));
//                        
//    }
//
//    /**
//     * Test of subscribeNewsNotifications method, of class RestControllers.
//     */
//    @Test
//    public void testSubscribeNewsNotifications() throws Exception {
//        
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//
//        UserDetails userDetails = accountRepository.loadUserByUsername("black@cat.com");
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
//        
//        SecurityContext securityContext = new SecurityContextImpl();
//        
//        securityContext.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(securityContext);
//        
//        System.out.println(((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
//        
//        String ac = "{\"notifications\":false}";
//        restUserMockMvc.perform(
//                        post("/subscribeNewsNotifications")
//                        .header("Content-Type", "application/json")
//                        .content(ac.getBytes())
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().string("ok"));
//    }
//
//    /**
//     * Test of subscribeNewMoviesNotifications method, of class RestControllers.
//     */
//    @Test
//    public void testSubscribeNewMoviesNotifications() throws Exception {
//        
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//
//        UserDetails userDetails = accountRepository.loadUserByUsername("black@cat.com");
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
//        
//        SecurityContext securityContext = new SecurityContextImpl();
//        
//        securityContext.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(securityContext);
//        
//        String ac = "{\"notifications\":false}";
//        restUserMockMvc.perform(
//                        post("/subscribeNewMoviesNotifications")
//                        .header("Content-Type", "application/json")
//                        .content(ac.getBytes())
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().string("ok"));
//    }
//    
//    /**
//     * Test of addTofavorite method, of class RestControllers.
//     */
//    @Test
//    public void testAddTofavorite() throws Exception {
//        
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//
//        UserDetails userDetails = accountRepository.loadUserByUsername("black@cat.com");
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
//        
//        SecurityContext securityContext = new SecurityContextImpl();
//        
//        securityContext.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(securityContext);
//        
//        String ac = "{\"genre\":[\"horror\"],\"subCategory\":[\"comic-thriller\",\"action-thriller\"]}";
//        
//        restUserMockMvc.perform(
//                        post("/addToFavorite")
//                        .header("Content-Type", "application/json")
//                        .content(ac.getBytes())
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().string("true"));
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    //                USER INDEPENDENT SERVICES
//    //                  search, get genre, get subcategory, signup
//    
//       /**
//     * Test of searchMovie method, of class RestControllers.
//     */
//    @Test
//    public void testSearchMovieOk() throws Exception {
//        restUserMockMvc.perform(
//                        get("/searchMovie?searchTerm=Pinocchio")
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                        .andExpect(jsonPath("$", hasSize(1)));
//    }
//    
//    /**
//     * Test of searchMovie method, of class RestControllers.
//     */
//    @Test
//    public void testSearchMovieWrong() throws Exception {
//        restUserMockMvc.perform(
//                        get("/searchMovie?searchTerm=Dracula")
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                        .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    /**
//     * Test of getGenres method, of class RestControllers.
//     */
//    @Test
//    public void testGetGenres() throws Exception {
//        restUserMockMvc.perform(
//                        get("/genres")
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                        .andExpect(jsonPath("$", hasSize(6)));
//    }
//
//    /**
//     * Test of getSubcategories method, of class RestControllers.
//     */
//    @Test
//    public void testGetSubcategories() throws Exception {
//        restUserMockMvc.perform(
//                        get("/subcategories")
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                        .andExpect(jsonPath("$", hasSize(36)));
//    }
//    
//        /**
//     * Test of signUp method, of class RestControllers.
//     */
//    @Test
//    public void testSignUp() throws Exception {
//        String ac = "{\"is_active\" : true, \"password\" : \"password\", \"email\" : \"random@guy.com\", \"creation_time\" : \"2017-01-20\", \"users\" : [ { \"dob\" : \"1950-08-11\", \"gender\" : \"unknown\", \"language\" : \"swearing\", \"is_active\" : true, \"creation_time\" : \"2017-01-20\", \"nickname\" : \"BlackCat\", \"favorites\" : {\"genre\":[], \"subCategory\" : []}, \"newsNotifications\" : true, \"newMoviesNotifications\" : true } ] }";
//        restUserMockMvc.perform(
//                        post("/signup")
//                        .header("Content-Type", "application/json")
//                        .content(ac.getBytes())
//                        .accept("application/json"))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(content().string("ok"));
//        repository.delete((Account)accountRepository.loadUserByUsername("random@guy.com"));
//    }
//
//}
