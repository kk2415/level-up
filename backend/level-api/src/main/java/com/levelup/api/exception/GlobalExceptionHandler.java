package com.levelup.api.exception;

import com.levelup.article.exception.ArticleException;
import com.levelup.channel.exception.ChannelException;
import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.web.dto.ExceptionResponse;
import com.levelup.common.web.dto.FieldExceptionResponse;
import com.levelup.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request)
    {
        log.error("{} - {} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI(), e.getStackTrace());

        ExceptionResponse response = ExceptionResponse.of(500, e.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("{} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI());

        ExceptionResponse response = FieldExceptionResponse.of(ErrorCode.INVALID_REQUEST_BODY, e.getBindingResult());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e, HttpServletRequest request)
    {
        log.error("{} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI());

        ExceptionResponse response = ExceptionResponse.of(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ExceptionResponse> handleMemberException(MemberException e, HttpServletRequest request)
    {
        log.error("{} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI());

        ExceptionResponse response = ExceptionResponse.of(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<ExceptionResponse> handleArticleException(ArticleException e, HttpServletRequest request)
    {
        log.error("{} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI());

        ExceptionResponse response = ExceptionResponse.of(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(ChannelException.class)
    public ResponseEntity<ExceptionResponse> handleChannelException(ChannelException e, HttpServletRequest request)
    {
        log.error("{} - {} - {}", e.getClass().getName(), e.getMessage(), request.getRequestURI());

        ExceptionResponse response = ExceptionResponse.of(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }
}