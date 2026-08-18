package com.ram.Authentication.Authorization.exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
