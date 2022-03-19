package com.realestate.exception.advice;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.DataNotFoundException;
import com.realestate.exception.RequiredFieldException;
import com.realestate.model.response.MessageResponse;
import com.realestate.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${exception.print-stack-trace:false}")
    private boolean printStackTrace;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MessageResponse error = MessageResponse.addErrorMessage(MessageUtil.VALIDATION_ERROR);

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return buildMessageDto(error, ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildMessageDto(MessageResponse.addErrorMessage(MessageUtil.UNKNOWN_ERROR), ex, status, request);
    }

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<Object> handleRequiredFieldException(RequiredFieldException ex, WebRequest request) {
        log.error("Required field can not be empty", ex);
        return buildMessageDto(ex.getMessageResponse(), ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
        log.error("Data not found", ex);
        return buildMessageDto(ex.getMessageResponse(), ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Bad request", ex);
        return buildMessageDto(ex.getMessageResponse(), ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        log.error("Unknown error occurred: ", ex);
        return buildMessageDto(MessageResponse.addErrorMessage(MessageUtil.UNKNOWN_ERROR), ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildMessageDto(MessageResponse error, Exception ex, HttpStatus status, WebRequest request) {
        if (printStackTrace) {
            error.setStackTrace(ExceptionUtils.getStackTrace(ex));
        }

        return ResponseEntity.status(status).body(error);
    }

}