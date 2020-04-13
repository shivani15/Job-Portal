package com.upraised.exceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends RuntimeException {
  public HttpNotFoundException(String message) {
    super(message);
  }

  public HttpNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}