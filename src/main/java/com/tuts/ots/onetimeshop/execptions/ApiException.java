package com.tuts.ots.onetimeshop.execptions;

public class ApiException extends RuntimeException{



    public  ApiException(String message){
        super(message);
    }

    public  ApiException(){
        super();
    }


}
