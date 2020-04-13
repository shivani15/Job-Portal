package com.upraised.exceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HttpForbiddenException extends RuntimeException {

  public HttpForbiddenException(String message) {
    super(message);
  }

  public HttpForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}