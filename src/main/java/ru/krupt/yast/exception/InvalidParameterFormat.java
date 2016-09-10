package ru.krupt.yast.exception;

import lombok.Getter;

@Getter
public class InvalidParameterFormat extends RuntimeException {

    private final String parameter;

    public InvalidParameterFormat(String parameter) {
        super(String.format("Parameter: \"%s\"", parameter));
        this.parameter = parameter;
    }

}
