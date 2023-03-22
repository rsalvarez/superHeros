package com.superhero.controller.advice;

import com.superhero.exception.ExResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Rafael
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ExResponse.class)
    public ResponseEntity<String> handleException(ExResponse ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getErrCode())).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getMessage());
    }



}
