package in.nishu.tradex.notification_service.controllers;

import in.nishu.tradex.common_lib.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> responseStatus(ResponseStatusException exception){
        int status=exception.getStatusCode().value();
        String error=exception.getStatusCode().toString();
        return ResponseEntity.status(status).body(ApiError.of(status,error,exception.getReason()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException exception){
        var details = exception.getBindingResult().getFieldErrors().stream()
                .map(this::fieldError)
                .toList();
        return ResponseEntity.badRequest()
                .body(ApiError.of(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                        "Validation failed", details));
    }

    private String fieldError(FieldError fieldError){
      return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
