package ru.krupt.yast;

import lombok.Value;

@Value
class Part {

    private static final int EMPTY_PARAMETER_VALUE = -1;

    String text;

    String beforeParameterValue;

    boolean parameterPresent;

    int parameterNo;

    String afterParameterValue;

    boolean defaultValuePresent;

    String defaultValue;

    public Part(String text) {
        this(text, null, EMPTY_PARAMETER_VALUE, null, false, null);
    }

    public Part(String text, String beforeParameterValue, int parameterNo, String afterParameterValue, boolean defaultValueExists, String defaultValue) {
        this.text = text;
        this.beforeParameterValue = beforeParameterValue;
        this.parameterPresent = parameterNo != EMPTY_PARAMETER_VALUE;
        this.parameterNo = parameterNo - 1;
        this.afterParameterValue = afterParameterValue;
        this.defaultValuePresent = defaultValueExists;
        this.defaultValue = defaultValue;
    }

    public int getParameterNoInTemplate() {
        return parameterNo + 1;
    }

}
