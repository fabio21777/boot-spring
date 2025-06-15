package com.boot.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
    private static final long serialVersionUID = 1L;
    @Getter
    private List<FieldMessage> errosFieldMessages = new ArrayList<>();

    public void addError (String fieldName, String message) {
        errosFieldMessages.add(new FieldMessage(fieldName,message));
    }
}