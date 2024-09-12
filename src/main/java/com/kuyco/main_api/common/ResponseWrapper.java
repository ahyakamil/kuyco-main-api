package com.kuyco.main_api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuyco.main_api.constant.ErrorType;
import com.kuyco.main_api.constant.InternalStatusCode;
import com.kuyco.main_api.exception.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
public class ResponseWrapper<T>{
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ResponseEntity<ResponseWrapper<T>> buildResponseOk(T data) {
        this.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        this.data = data;
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildResponseOk() {
        this.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildResponseCreated() {
        this.statusCode = InternalStatusCode.GENERAL_SUCCESS;
        return ResponseEntity.status(HttpStatus.CREATED).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildGeneralException(Exception e) {
        this.statusCode = InternalStatusCode.GENERAL_ERROR;
        this.error = ErrorType.ERROR_GENERAL;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildConflictException(ConflictException e) {
        this.statusCode = InternalStatusCode.GENERAL_WARNING;
        this.error = ErrorType.ERROR_CONFLICT;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildNotFoundException(NotFoundException e) {
        this.statusCode = InternalStatusCode.GENERAL_WARNING;
        this.error = ErrorType.ERROR_NOT_FOUND;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildBadRequestException(BadRequestException e) {
        this.statusCode = InternalStatusCode.GENERAL_WARNING;
        this.error = ErrorType.ERROR_BAD_REQUEST;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildUnprocessableEntityException(UnprocessableEntityException e) {
        this.statusCode = InternalStatusCode.GENERAL_WARNING;
        this.error = ErrorType.ERROR_UNPROCESSABLE_ENTITY;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(this);
    }

    public ResponseEntity<ResponseWrapper<T>> buildUnauthorizedException(UnauthorizedException e) {
        this.statusCode = InternalStatusCode.GENERAL_WARNING;
        this.error = ErrorType.ERROR_UNAUTHORIZED;
        this.message = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this);
    }
}