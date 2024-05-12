package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.entities.Role;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.PasswordReq;
import com.tuts.ots.onetimeshop.payloads.UserDto;
import com.tuts.ots.onetimeshop.payloads.UserResponse;
import com.tuts.ots.onetimeshop.repositires.RoleRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.security.JwtTokenHelper;
import com.tuts.ots.onetimeshop.services.DeleteService;
import com.tuts.ots.onetimeshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public boolean changePassword(Integer userId, PasswordReq passwordReq) {
        // Retrieve user from the database
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        if (user == null) {
            return false;

        }

        if (!passwordEncoder.matches(passwordReq.getOldPassword(), user.getPassword())) {
            return false;

        }
        String newPasswordEncoded = passwordEncoder.encode(passwordReq.getNewpassword());
        user.setPassword(newPasswordEncoded);

        // Save the updated user to the database
        User usersave = this.userRepo.save(user);


        return true;
    }


    //register user
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encoded Password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto registerAdminUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encoded Password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
        Role role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();

        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User user1 = this.modelMapper.map(userDto, User.class);
        user1.setUserPic("default.png");
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setUserPic(userDto.getUserPic());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        user.setPhonenumber(userDto.getPhonenumber());
        user.setCity(userDto.getCity());
        user.setAddress(userDto.getAddress());
        user.setCNIC(userDto.getCNIC());
        user.setShopName(userDto.getShopName());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<User> pageusers = this.userRepo.findAll(p);

        List<User> allusers = pageusers.getContent();
        List<UserDto> userDtos = allusers.stream()
                .map(user -> this.userToDto(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDtos);
        userResponse.setPageNumber(pageusers.getNumber());
        userResponse.setPageSize(pageusers.getSize());
        userResponse.setTotalElement(pageusers.getTotalElements());

        userResponse.setTotalPages(pageusers.getTotalPages());
        userResponse.setLastPage(pageusers.isLast());
        return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        this.userRepo.delete(user);
    }




    public User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setRole(userDto.getRole());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setRole(user.getRole());
        return userDto;
    }

}
