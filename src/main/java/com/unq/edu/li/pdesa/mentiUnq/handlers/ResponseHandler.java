package com.unq.edu.li.pdesa.mentiUnq.handlers;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.UnauthorizedException;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundError(EntityNotFoundException ex) {
        return buildResponseEntity(new ResponseUnit(Status.FAIL, ex.getMessage(), ""), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return buildResponseEntity(new ResponseUnit(Status.FAIL, ex.getMessage(), ""), HttpStatus.UNAUTHORIZED);
    }
    private ResponseEntity<Object> buildResponseEntity(ResponseUnit response, HttpStatus statusCode) {
        return new ResponseEntity<>(response, statusCode);
    }
}
