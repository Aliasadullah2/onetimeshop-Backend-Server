package com.tuts.ots.onetimeshop.services;


import com.tuts.ots.onetimeshop.payloads.PasswordReq;
import com.tuts.ots.onetimeshop.payloads.UserDto;
import com.tuts.ots.onetimeshop.payloads.UserResponse;

public interface UserService {




    boolean changePassword(Integer userId, PasswordReq passwordReq);

    UserDto registerNewUser(UserDto user);

    UserDto registerAdminUser(UserDto user);

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize,String sortBy);
    void deleteUser(Integer userId);







}
