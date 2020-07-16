package com.wzp.pet.exceptions;

public class UserOperationException extends RuntimeException {
    private static final long serialVersionUID=2345687694784560987L;
    public UserOperationException(String msg){super(msg);}
}
