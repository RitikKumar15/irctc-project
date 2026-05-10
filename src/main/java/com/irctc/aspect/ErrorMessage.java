package com.irctc.aspect;

import lombok.Builder;
import lombok.Data;

@Builder
public class ErrorMessage {
    private int statusCode;
    private String message;
}