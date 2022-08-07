package com.levelup.core.exception;

import com.levelup.core.dto.exception.ExceptionResponse;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.exception.channel.NoPlaceChnnelException;
import com.levelup.core.exception.channelMember.DuplicateChannelMemberException;
import com.levelup.core.exception.member.DuplicateEmailException;
import com.levelup.core.exception.emailAuth.EmailCodeExpiredException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.member.NotConfirmedEmailException;
import com.levelup.core.exception.vote.DuplicateVoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception e, HttpServletRequest request) {
//        log.error(e.getClass().getName(), e.getMessage());
//
//        String exceptionDir = e.getClass().getName();
//        ExceptionResponse exceptionResponse = ExceptionResponse.of(
//                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
//        );
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(MemberNotFoundException.class)
    public final ResponseEntity<Object> memberNotFoundException(MemberNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> duplicateEmailExceptionHandler(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundImageException(ImageNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionResponse> postNotFoundException(PostNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(NotLoggedInException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotConfirmedEmailException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(NotConfirmedEmailException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPlaceChnnelException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(NoPlaceChnnelException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateChannelMemberException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(DuplicateChannelMemberException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailCodeExpiredException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(EmailCodeExpiredException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(DuplicateVoteException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(), e.getMessage(), exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1), request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
