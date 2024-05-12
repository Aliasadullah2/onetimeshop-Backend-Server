package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import com.tuts.ots.onetimeshop.execptions.ApiException;
import com.tuts.ots.onetimeshop.payloads.CategoryResponse;
import com.tuts.ots.onetimeshop.payloads.JwtAuthRequest;
import com.tuts.ots.onetimeshop.payloads.JwtAuthRespone;
import com.tuts.ots.onetimeshop.payloads.UserDto;
import com.tuts.ots.onetimeshop.security.JwtTokenHelper;
import com.tuts.ots.onetimeshop.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")

public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthRespone> createToken(
            @Valid @RequestBody JwtAuthRequest request
            ) throws Exception {
        this.authenticate(request.getUsername(),request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthRespone respone=new JwtAuthRespone();
        respone.setToken(token);
        respone.setUserDto(this.modelMapper.map((User)userDetails,UserDto.class));

        return new ResponseEntity<JwtAuthRespone>(respone, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

       try{
           this.authenticationManager.authenticate(authenticationToken);
       }catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw  new ApiException("Invalid Username & Passwords");
       }

    }

    //register new user api

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto){
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        User user1 = this.modelMapper.map(userDto, User.class);
        user1.setUserPic("default.png");
        return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }

    @PostMapping("/Admin/register")
    public ResponseEntity<UserDto> registerAdminUser(@Valid @RequestBody UserDto userDto){
        UserDto registeredUser = this.userService.registerAdminUser(userDto);
        User user1 = this.modelMapper.map(userDto, User.class);
        user1.setUserPic("default.png");
        return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }




}
