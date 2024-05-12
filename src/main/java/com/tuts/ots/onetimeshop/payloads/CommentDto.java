package com.tuts.ots.onetimeshop.payloads;

import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CommentDto {

    private Integer comId;

    @NotEmpty
    @Size(min = 5,message = "Comment must be not Null Characters")
    private String Content;

    private Date addedCommentDate;

    private UserDto user;










}
