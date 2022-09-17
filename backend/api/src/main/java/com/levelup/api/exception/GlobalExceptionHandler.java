package com.levelup.api.exception;

import com.levelup.api.exception.article.ArticleNotFoundException;
import com.levelup.api.exception.emailAuth.SecurityCodeExpiredException;
import com.levelup.api.exception.emailAuth.NotMatchSecurityCodeException;
import com.levelup.api.exception.member.EmailDuplicationException;
import com.levelup.api.exception.member.MemberNotFoundException;
import com.levelup.api.exception.vote.VoteDuplicationException;
import com.levelup.api.exception.channel.NoPlaceChannelException;
import com.levelup.api.exception.channelMember.ChannelMemberDuplicationException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.BAD_REQUEST.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VoteDuplicationException.class)
    public ResponseEntity<ExceptionResponse> duplicateVoteException(
            VoteDuplicationException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.VOTE_DUPLICATION.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailDuplicationException.class)
    public ResponseEntity<ExceptionResponse> duplicateEmailExceptionHandler(
            Exception e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.EMAIL_DUPLICATION.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChannelMemberDuplicationException.class)
    public ResponseEntity<ExceptionResponse> duplicateChannelMemberException(
            ChannelMemberDuplicationException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.CHANNEL_MEMBER_DUPLICATION.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public final ResponseEntity<Object> memberNotFoundException(
            MemberNotFoundException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.MEMBER_NOT_FOUND.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundImageException(
            ImageNotFoundException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.IMAGE_NOT_FOUND.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> postNotFoundException(
            ArticleNotFoundException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.ARTICLE_NOT_FOUND.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoPlaceChannelException.class)
    public ResponseEntity<ExceptionResponse> noPlaceChannelException(
            NoPlaceChannelException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.NO_PLACE_CHANNEL.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityCodeExpiredException.class)
    public ResponseEntity<ExceptionResponse> emailCodeExpiredException(
            SecurityCodeExpiredException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.SECURITY_CODE_EXPIRED.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchSecurityCodeException.class)
    public ResponseEntity<ExceptionResponse> notMatchSecurityCodeException(
            NotMatchSecurityCodeException e,
            HttpServletRequest request)
    {
        log.error(e.getClass().getName(), e.getMessage());

        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = ExceptionResponse.of(
                LocalDateTime.now(),
                ErrorCode.NOT_MATCH_SECURITY_CODE.getMessage(),
                exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1),
                request.getRequestURI()
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}