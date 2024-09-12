package com.kuyco.main_api.config;

import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConflictException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleConflict(ConflictException e) {
        return new ResponseWrapper<>().buildConflictException(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleGeneral(Exception e) {
        return new ResponseWrapper<>().buildGeneralException(e);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleNotFound(NotFoundException e) {
        return new ResponseWrapper<>().buildNotFoundException(e);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleBadRequest(BadRequestException e) {
        return new ResponseWrapper<>().buildBadRequestException(e);
    }

    @ExceptionHandler(value = {UnprocessableEntityException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleUnprocessableEntity(UnprocessableEntityException e) {
        return new ResponseWrapper<>().buildUnprocessableEntityException(e);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleUnauthorized(UnauthorizedException e) {
        return new ResponseWrapper<>().buildUnauthorizedException(e);
    }
}