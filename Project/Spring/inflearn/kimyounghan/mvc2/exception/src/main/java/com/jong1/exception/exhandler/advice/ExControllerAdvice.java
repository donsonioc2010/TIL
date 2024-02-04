package com.jong1.exception.exhandler.advice;

import com.jong1.exception.exception.UserException;
import com.jong1.exception.exhandler.ErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
@RestControllerAdvice(
        annotations = RestController.class,     // 해당 어노테이션이 붙은 클래스에만 동작한다, 없으면 글로벌로
        basePackages = "com.jong1.exception.api" // 해당 패키지 내에서만 동작한다
//        assignableTypes = {ExControllerAdvice.class} // 해당 클래스에만 동작한다
)
@RequiredArgsConstructor
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Annotation을 사용하지 않는 경우 Status가 200으로 반환된다
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler //Exception타입을 생략해도 파라미터에 정의가 되어있으면 해당 타입을 찾아서 처리한다
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
