package com.levelup.api.exception;

import com.levelup.api.exception.article.ArticleNotFoundException;
import com.levelup.api.exception.emailAuth.EmailCodeExpiredException;
import com.levelup.api.exception.emailAuth.NotMatchSecurityCodeException;
import com.levelup.api.exception.member.DuplicateEmailException;
import com.levelup.api.exception.member.MemberNotFoundException;
import com.levelup.api.exception.member.NotConfirmedEmailException;
import com.levelup.api.exception.vote.DuplicateVoteException;
import com.levelup.api.exception.channel.NoPlaceChannelException;
import com.levelup.api.exception.channelMember.DuplicateChannelMemberException;
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

    @ExceptionHandler(MemberNotFoundException.class)
    public final ResponseEntity<Object> memberNotFoundException(MemberNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> duplicateEmailExceptionHandler(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
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
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundImageException(ImageNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> postNotFoundException(ArticleNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<ExceptionResponse> notLoggedInException(NotLoggedInException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotConfirmedEmailException.class)
    public ResponseEntity<ExceptionResponse> notConfirmedEmailException(NotConfirmedEmailException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPlaceChannelException.class)
    public ResponseEntity<ExceptionResponse> noPlaceChannelException(NoPlaceChannelException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateChannelMemberException.class)
    public ResponseEntity<ExceptionResponse> duplicateChannelMemberException(DuplicateChannelMemberException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailCodeExpiredException.class)
    public ResponseEntity<ExceptionResponse> emailCodeExpiredException(EmailCodeExpiredException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<ExceptionResponse> duplicateVoteException(DuplicateVoteException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchSecurityCodeException.class)
    public ResponseEntity<ExceptionResponse> notMatchSecurityCodeException(NotMatchSecurityCodeException e, HttpServletRequest request) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                e.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
