package com.tuts.ots.onetimeshop.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class VenderDto {


    private Integer venId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String about;

    @NotEmpty
    private String price;


    private Date addedDate;

    @NotEmpty
    private String phonenumber;

    private String venImg;





    private CatagoryDto category;


    private UserDto user;
}
