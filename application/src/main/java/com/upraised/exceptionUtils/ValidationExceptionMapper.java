package com.upraised.exceptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationExceptionMapper extends ResponseEntityExceptionHandler {

  private class ApiError {

    private HttpStatus status;
    private List<String> errors;

    public ApiError(HttpStatus status, List<String> errors) {
      super();
      this.status = status;
      this.errors = errors;
    }

    public ApiError(HttpStatus status, String error) {
      super();
      this.status = status;
      errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
      return status;
    }

    public List<String> getErrors() {
      return errors;
    }
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> details = new ArrayList<>();
    for(ObjectError error : ex.getBindingResult().getAllErrors()) {
      String s = error.getDefaultMessage();
      if (error instanceof FieldError) {
        s = ((FieldError) error).getField() + " " + s;
      }
      details.add(s);
    }
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST, details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
}