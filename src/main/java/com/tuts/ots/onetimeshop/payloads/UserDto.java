package com.tuts.ots.onetimeshop.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuts.ots.onetimeshop.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4,message = "UserName must be 4 Characters")
    private String name;

    @NotEmpty(message = "Enter your Valid Email")
    @Email(message = "Enter your Valid Email")
    private String email;
//    @NotEmpty
//    @Size(min = 8,max = 50,message = "Password Must be 8 Characters Max Charaters 50")
    private String password;

    @NotEmpty
    private String phonenumber;


    private String City;


    private String Address;


    private String CNIC;

    private Set<RoleDto> roles = new HashSet<>();



    private String ShopName;

    private String UserPic;



    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password){
        this.password=password;
    }
}
