package com.tuts.ots.onetimeshop.payloads;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.Comment;
import com.tuts.ots.onetimeshop.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Integer prodId;

    @NotEmpty

    private String prodName;

    @NotEmpty

    private String prodDesc;

    @NotEmpty
    private String prodPrice;





    private String prodImg ;

    private Date addedDate;



//    @NotEmpty
//    private String prodImageName;

    private CatagoryDto category;


    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
