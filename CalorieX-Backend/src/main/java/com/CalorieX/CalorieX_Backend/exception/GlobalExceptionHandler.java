package com.CalorieX.CalorieX_Backend.exception;

import com.CalorieX.CalorieX_Backend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MealNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleMealNotFoundException(MealNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            UserNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            GoalNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleGoalNotFoundException(GoalNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            EmailAlreadyExistsException.class
    )
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(
            EmailIsFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleEmailIsNotFoundException(EmailIsFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(
            PasswordIsIncorrectException.class
    )
    public ResponseEntity<ErrorResponse> handlePasswordIsIncorrectException(PasswordIsIncorrectException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )

    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        String message = ex
                .getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        errorResponse.setMessage(message);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
