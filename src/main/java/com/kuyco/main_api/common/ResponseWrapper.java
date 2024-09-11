package com.kuyco.main_api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuyco.main_api.constant.InternalStatusCode;
import com.kuyco.main_api.exception.BadRequestException;
import com.kuyco.main_api.exception.ConflictException;
import com.kuyco.main_api.exception.NotFoundException;
import com.kuyco.main_api.exception.UnprocessableEntityException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseWrapper<T>{
    int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public ResponseEntity<ResponseWrapper<T>> buildResponseOk(T data) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        responseWrapper.data = data;
        return ResponseEntity.ok(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildResponseOk() {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        return ResponseEntity.ok(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildResponseCreated() {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildGeneralException(Exception e) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_ERROR;
        responseWrapper.error = ErrorType.ERROR_GENERAL;
        responseWrapper.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildConflictException(ConflictException e) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_WARNING;
        responseWrapper.error = ErrorType.ERROR_CONFLICT;
        responseWrapper.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildNotFoundException(NotFoundException e) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_WARNING;
        responseWrapper.error = ErrorType.ERROR_NOT_FOUND;
        responseWrapper.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildBadRequestException(BadRequestException e) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_WARNING;
        responseWrapper.error = ErrorType.ERROR_BAD_REQUEST;
        responseWrapper.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
    }

    public ResponseEntity<ResponseWrapper<T>> buildUnprocessableEntity(UnprocessableEntityException e) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<T>();
        responseWrapper.statusCode = InternalStatusCode.GENERAL_WARNING;
        responseWrapper.error = ErrorType.ERROR_UNPROCESSABLE_ENTITY;
        responseWrapper.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseWrapper);
    }
}