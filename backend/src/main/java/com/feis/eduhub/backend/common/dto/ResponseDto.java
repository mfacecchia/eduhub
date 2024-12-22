package com.feis.eduhub.backend.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response class for a consistent response body. Includes a {@code statusCode},
 * a custom {@code message} and optional {@code data} field (used mainly to send
 * data possibly fetched from a database or as a result of some data
 * processing). Build a response using its
 * {@link com.feis.eduhub.backend.common.dto.ResponseDto.ResponseBuilder
 * ResponseBuilder class}.
 */
public class ResponseDto<T> {
    @JsonProperty(value = "status", required = true)
    private final int statusCode;
    @JsonProperty(value = "message", required = false, defaultValue = "")
    private final String message;
    @JsonProperty(value = "data", required = false)
    private final T data;

    private ResponseDto(ResponseBuilder<T> builder) {
        statusCode = builder.statusCode;
        message = builder.message;
        data = builder.data;
    }

    // TODO: Make default response classes (such as success and error)
    public static class ResponseBuilder<T> {
        private final int statusCode;
        private String message;
        private T data;

        public ResponseBuilder(int statusCode) {
            this.statusCode = statusCode;
        }

        public ResponseBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<>(this);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}