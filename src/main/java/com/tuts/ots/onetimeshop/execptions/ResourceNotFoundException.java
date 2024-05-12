package com.tuts.ots.onetimeshop.execptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
        String resourcename;
        String fieldname;
        long fieldvalue;

    private  String user;
    private String email;
    private String username;

    public ResourceNotFoundException(String resourcename, String fieldname, long fieldvalue) {
        super(String.format("%s not found with %s : %s", resourcename,fieldname,fieldvalue));
        this.resourcename = resourcename;
        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
    }

    public ResourceNotFoundException(String user, String email, String username) {
        super(String.format("%s not found with %s : %s", user,email,username));
        this.user = user;
        this.email = email;
        this.username = username;
    }
}
