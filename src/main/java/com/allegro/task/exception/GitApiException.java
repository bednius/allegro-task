package com.allegro.task.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class GitApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public GitApiException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }
}
