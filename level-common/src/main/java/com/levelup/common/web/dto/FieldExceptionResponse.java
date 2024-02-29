package com.levelup.common.web.dto;

import com.levelup.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FieldExceptionResponse extends ExceptionResponse {

    private final List<FieldError> fieldErrors;

    private FieldExceptionResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
        super(errorCode);
        this.fieldErrors = fieldErrors;
    }

    public static FieldExceptionResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new FieldExceptionResponse(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    public static class FieldError {

        private final String field;
        private final String value;
        private final String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    ))
                    .collect(Collectors.toUnmodifiableList());
        }
    }
}
