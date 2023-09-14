package com.vaistra.master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleGlobalRegularException(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorMap;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, String> exceptionHandler(BadCredentialsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", "Request body cannot be empty");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", "Invalid Argument");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", "Invalid Argument");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicateEntryException(DuplicateEntryException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleUnauthorizedException(UnauthorizedException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoDataFoundException.class)
    public Map<String, String> handleNoDataFoundException(NoDataFoundException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IsActiveExceptionHandler.class)
    public Map<String, String> handleIsActiveException(IsActiveExceptionHandler ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IsDeleteExceptionHandler.class)
    public Map<String, String> handleIsDeleteException(IsDeleteExceptionHandler ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public Map<String, String> handleIoException(IOException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException ex)
    {
        return Map.of("errorMessage", ex.getMessage());
    }
}

