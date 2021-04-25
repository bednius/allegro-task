package com.allegro.task.handler;

import com.allegro.task.exception.GitApiException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GitApiException.class)
    public ResponseEntity<Object> handleGitApiException(GitApiException ex) {
        log.error("Error occurred while requesting git api {}", ex.toString());
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}
