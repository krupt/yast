package ru.krupt.yast;

import lombok.Value;

import java.util.OptionalInt;

@Value
class Part {

    private static final int EMPTY_PARAMETER_VALUE = -1;

    String text;

    String beforeParameterValue;

    OptionalInt parameterNo;

    String afterParameterValue;

    boolean defaultValueExists;

    String defaultValue;

    public Part(String text) {
        this(text, null, EMPTY_PARAMETER_VALUE, null, false, null);
    }

    public Part(String text, String beforeParameterValue, int parameterNo, String afterParameterValue, boolean defaultValueExists, String defaultValue) {
        this.text = text;
        this.beforeParameterValue = beforeParameterValue;
        this.parameterNo = parameterNo == EMPTY_PARAMETER_VALUE ? OptionalInt.empty() : OptionalInt.of(parameterNo);
        this.afterParameterValue = afterParameterValue;
        this.defaultValueExists = defaultValueExists;
        this.defaultValue = defaultValue;
    }

}
