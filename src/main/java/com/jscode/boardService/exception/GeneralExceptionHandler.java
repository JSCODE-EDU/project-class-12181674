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

import static com.jscode.boardService.domain.ExceptionMessageConst.UNEXPECTED_EXCEPTION;
import static com.jscode.boardService.domain.ExceptionMessageConst.WRONG_TOKEN;


@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse IllegalArgumentHandler(IllegalArgumentException e) {

        log.error(e.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<ErrorResponse> errorResponse = getErrorResponse(e.getBindingResult());

        log.error(errorResponse.toString());

        return errorResponse;
    }

    @ExceptionHandler(IllegalAccessError.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse tokenException(RuntimeException e){

        log.error(e.getMessage());

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), e.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse except(RuntimeException e) {

        log.error(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), UNEXPECTED_EXCEPTION.getMessage());

        return errorResponse;
    }

    private List<ErrorResponse> getErrorResponse(BindingResult bindingResult){

        List<ErrorResponse> result = new ArrayList<ErrorResponse>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), fieldError.getDefaultMessage());
            result.add(errorResponse);
        }
        return result;
    }
}
