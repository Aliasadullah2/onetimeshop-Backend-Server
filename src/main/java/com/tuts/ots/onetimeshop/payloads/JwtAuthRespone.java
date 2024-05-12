package com.tuts.ots.onetimeshop.payloads;

import lombok.Data;

@Data
public class JwtAuthRespone {

    private String token;

    private UserDto userDto;
}
