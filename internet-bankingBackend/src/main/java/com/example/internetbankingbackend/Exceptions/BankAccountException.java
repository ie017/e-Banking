package com.example.internetbankingbackend.Exceptions;

public class BankAccountException extends RuntimeException{
    public BankAccountException(String message){
        super(message);
    }
}
