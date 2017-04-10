package com.pushserver.infrastructure;

import com.pushserver.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleGeneralException(Exception ex) {
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setErrorCode("internal_error");
        exceptionBody.setMessage("Internal server error occurred");

        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionBody);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionBody> handleApplicationException(ApplicationException ex) {
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setErrorCode(ex.getErrorCode());
        exceptionBody.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(exceptionBody);
    }
}
