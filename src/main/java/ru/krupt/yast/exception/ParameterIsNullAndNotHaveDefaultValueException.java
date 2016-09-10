package ru.krupt.yast.exception;

import lombok.Getter;

@Getter
public class ParameterIsNullAndNotHaveDefaultValueException extends RuntimeException {

    private final int parameterNo;

    public ParameterIsNullAndNotHaveDefaultValueException(int parameterNo) {
        super("Parameter #" + parameterNo);
        this.parameterNo = parameterNo;
    }

}
