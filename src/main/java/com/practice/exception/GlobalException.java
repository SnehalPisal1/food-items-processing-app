package com.practice.exception;

import java.util.logging.Logger;

public class GlobalException {

    private static final Logger logger = Logger.getLogger(GlobalException.class.getName());

    public static void handleException(Exception ex){
        if(ex instanceof CsvParsingException){
            logger.info("Csv Parsing Error : "+ ex.getMessage());
        } else if (ex instanceof IllegalArgumentException) {
            logger.info("Illegal Argument Exception : "+ ex.getMessage());
        } else {
            logger.info("Unexpected Error : "+ ex.getMessage() +" Cause : "+ ex.getStackTrace());
        }
    }
}
