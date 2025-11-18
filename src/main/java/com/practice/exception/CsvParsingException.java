package com.practice.exception;

public class CsvParsingException extends RuntimeException{

    public CsvParsingException(String message){
        super(message);
    }
}
