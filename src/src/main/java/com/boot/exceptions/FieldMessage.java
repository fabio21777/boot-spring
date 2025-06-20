package com.boot.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
public class FieldMessage  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String message;

}