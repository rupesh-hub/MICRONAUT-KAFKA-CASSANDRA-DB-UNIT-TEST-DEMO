package com.rupesh.exception;

import java.util.Map;

public class CustomValidationException extends RuntimeException {

    private Map<String, String> validationMessages;

    public CustomValidationException(Map<String, String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public Map<String, String> getValidationMessages() {
        return validationMessages;
    }

}