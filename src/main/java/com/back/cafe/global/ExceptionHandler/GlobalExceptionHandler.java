package com.back.cafe.global.ExceptionHandler;

import com.back.cafe.global.rsData.RsData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RsData<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        // 메시지에 "재고"가 있으면 BAD_REQUEST(400), 없으면 NOT_FOUND(404)
        HttpStatus status = e.getMessage().contains("재고")
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(new RsData<>(
                        e.getMessage(),
                        status.value() + "-1" // 400-1 또는 404-1이 나감
                ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RsData<Void>> handlerNoSucjElementException(NoSuchElementException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RsData<>(
                        e.getMessage(),
                        "404-1"
                ));
    }
}
