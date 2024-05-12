package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.*;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.services.DeleteService;
import com.tuts.ots.onetimeshop.services.FileService;
import com.tuts.ots.onetimeshop.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image.userprofile}")
    private String path;

    //create user api
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
        UserDto createUserDto =this.userService.createUser(userDto);

        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //updated user api
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId){
        UserDto updatedUser =this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }


    //Admin
    //Delete User api
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
        this.deleteService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully",true), HttpStatus.OK);
    }

    //Get User api
    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy
    ){
        UserResponse userResponse =this.userService.getAllUsers(pageNumber,pageSize,String.valueOf(Sort.by(sortBy)));

        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
//        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //singal user api
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @PostMapping("/{userId}/changepassword")
    public ResponseEntity<Boolean> changePassword(
            @PathVariable Integer userId,
            @RequestBody PasswordReq passwordReq) {

        try {
            boolean updatedUser = userService.changePassword(userId, passwordReq);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    //PostImage Upload
    @PostMapping("/user/image/upload/{userId}")
    public ResponseEntity<UserDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("userId") Integer userId


    ) throws IOException {
        UserDto userDto =this.userService.getUserById(userId);

        String fileName= this.fileService.uploadImage(path,image);

        userDto.setUserPic(fileName);

        UserDto updatedUserDto = this.userService.updateUser(userDto,userId);
        return  new ResponseEntity<UserDto>(updatedUserDto,HttpStatus.OK);
    }

    @GetMapping(value = "/user/image/{imagename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imagename") String imagename,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imagename);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
