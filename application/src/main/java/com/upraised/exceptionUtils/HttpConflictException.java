package com.upraised.exceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HttpConflictException extends RuntimeException {
  public HttpConflictException(String message) {
    super(message);
  }

  public HttpConflictException(String message, Throwable cause) {
    super(message, cause);
  }
}