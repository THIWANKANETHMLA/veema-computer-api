package lk.ijse.veema_computer.exception;

import jakarta.validation.ConstraintViolationException;
import lk.ijse.veema_computer.constant.CommonResponse;
import lk.ijse.veema_computer.constant.ResponseCode;
import lk.ijse.veema_computer.constant.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        log.warn("Resource not found: {}", exception.getMessage());

        CommonResponse<Object> response =
                new CommonResponse<Object>(
                        ResponseCode.NOT_FOUND,
                        null,
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CommonResponse<Object>> handleDuplicateResource(
            DuplicateResourceException exception) {

        log.warn("Duplicate resource: {}", exception.getMessage());

        CommonResponse<Object> response =
                new CommonResponse<Object>(
                        ResponseCode.CONFLICT,
                        null,
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        CommonResponse<Object> response =
                new CommonResponse<Object>(
                        ResponseCode.BAD_REQUEST,
                        errors,
                        ResponseMessage.VALIDATION_FAILED
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<Object>> handleConstraintViolation(
            ConstraintViolationException exception) {

        CommonResponse<Object> response =
                new CommonResponse<Object>(
                        ResponseCode.BAD_REQUEST,
                        null,
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException exception) {

        log.warn("Invalid JSON request");

        CommonResponse<Object> response =
                new CommonResponse<Object>(
                        ResponseCode.BAD_REQUEST,
                        null,
                        ResponseMessage.INVALID_JSON
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleGeneralException(
            Exception exception
    ) {
        if (exception instanceof ErrorResponse springError
                && springError.getStatusCode().is4xxClientError()) {

            String message = springError.getBody().getDetail();

            if (message == null) {
                message = "Request could not be processed";
            }

            CommonResponse<Object> response = new CommonResponse<>(
                    springError.getStatusCode().value(),
                    null,
                    message
            );

            return ResponseEntity
                    .status(springError.getStatusCode())
                    .headers(springError.getHeaders())
                    .body(response);
        }

        log.error("Unexpected server error", exception);

        CommonResponse<Object> response = new CommonResponse<>(
                ResponseCode.INTERNAL_ERROR,
                null,
                ResponseMessage.INTERNAL_ERROR
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
