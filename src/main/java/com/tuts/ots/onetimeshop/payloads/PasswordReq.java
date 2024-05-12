package com.tuts.ots.onetimeshop.payloads;

import lombok.Data;

@Data
public class PasswordReq {
    private String newpassword;
    private String oldPassword;

    UserDto userDto = new UserDto();

    public void getOldPassword(String oldPassword) {
        this.oldPassword = userDto.getPassword();
    }
}
