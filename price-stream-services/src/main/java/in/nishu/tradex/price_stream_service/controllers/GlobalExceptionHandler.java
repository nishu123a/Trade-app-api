package in.nishu.tradex.price_stream_service.controllers;

import in.nishu.tradex.common_lib.api.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validationError(MethodArgumentNotValidException exception){
         List details =exception.getBindingResult().getFieldErrors().stream().map(this::format).toList();
        return ResponseEntity.badRequest().body(ApiError.of(400,"Bad request","Validation failed",details));

    }

  @ExceptionHandler(ResolutionException.class)
   ResponseEntity<ApiError> responseStatusError(ResponseStatusException exception){
        int status=exception.getStatusCode().value();
        String error=exception.getStatusCode().toString();
        return ResponseEntity.status(status).body(ApiError.of(status,error,exception.getReason()));
  }
    private Object format(FieldError error) {
        return error.getField()+": "+error.getDefaultMessage();
    }
}
