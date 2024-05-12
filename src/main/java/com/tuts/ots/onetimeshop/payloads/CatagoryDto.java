package com.tuts.ots.onetimeshop.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CatagoryDto {
    private Integer id;
    @NotEmpty
    @Size(min = 5,message = "Category Title must be 5 Characters")
    private String categorytitle;




    private UserDto user;
}
