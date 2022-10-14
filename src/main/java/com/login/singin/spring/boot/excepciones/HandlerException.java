
package com.login.singin.spring.boot.excepciones;

import com.fasterxml.jackson.core.JsonParseException;
import com.login.singin.spring.boot.DTO.ErrorDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.lettuce.core.internal.Exceptions;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.exceptions.JedisConnectionException;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(value = RedisConnectionFailureException .class)
    public ResponseEntity<ErrorDTO> JedisConnectionExceptionHandler(JedisConnectionException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<ErrorDTO> SignatureExceptionHandler(SignatureException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<ErrorDTO> MalformedJwtExceptionHandler(MalformedJwtException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ErrorDTO> ExpiredJwtExceptionHandler(ExpiredJwtException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<ErrorDTO> UnsupportedJwtExceptionHandler(UnsupportedJwtException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> IllegalArgumentExceptionHandler(IllegalArgumentException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
        @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> IllegalArgumentExceptionHandler(JsonParseException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getLocalizedMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
        /*@ExceptionHandler(value = TemplateInputException.class)
    public ResponseEntity<ErrorDTO> TemplateInputExceptionHandler(Exceptions  ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.toString()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }*/
    
}
