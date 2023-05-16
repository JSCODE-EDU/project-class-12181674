package com.jscode.boardService.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String IllegalArgumentHandler(IllegalArgumentException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<String> ErrorMeassage = getErrorMessage(e.getBindingResult());

        log.error(ErrorMeassage.toString());

        return ErrorMeassage;
    }

    private List<String> getErrorMessage(BindingResult bindingResult){
        List<String> result = new ArrayList<String>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            result.add(fieldError.getDefaultMessage());
        }
        return result;
    }
}
