package ru.krupt.yast;

import ru.krupt.yast.exception.InvalidParameterFormat;
import ru.krupt.yast.exception.ParameterIsNullAndNotHaveDefaultValueException;
import ru.krupt.yast.util.IgnoreNullAndRemoveQuotesStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {

    private static final Pattern FORMAT_PATTERN = Pattern.compile("('.*')?(\\d{1,2})('.*?')?(\\?('.*')?)?", Pattern.DOTALL);
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\$\\{.*?\\}", Pattern.DOTALL);
    private static final int START_DELIMITER_LENGTH = 2;
    private static final int END_DELIMITER_LENGTH = 1;

    private final Iterable<Part> parts;

    public Template(String template) throws InvalidParameterFormat {
        if (template == null) {
            throw new NullPointerException("Template can't be null");
        }
        parts = parseTemplate(template);
    }

    private Iterable<Part> parseTemplate(String template) throws InvalidParameterFormat {
        ArrayList<Part> parts = new ArrayList<Part>();
        Matcher parameterFinder = PARAMETER_PATTERN.matcher(template);
        int end = 0;
        while (parameterFinder.find()) {
            int start = parameterFinder.start();
            int parameterEnd = parameterFinder.end();
            String paramString = template.substring(start + START_DELIMITER_LENGTH, parameterEnd - END_DELIMITER_LENGTH);
            Matcher formatMatcher = FORMAT_PATTERN.matcher(paramString);
            if (!formatMatcher.matches()) {
                throw new InvalidParameterFormat(paramString);
            }
            String text = template.substring(end, start);
            String before = formatMatcher.group(1);
            int parameterNo = Integer.parseInt(formatMatcher.group(2));
            String after = formatMatcher.group(3);
            String defaultValueGroup = formatMatcher.group(4);
            String defaultValue = formatMatcher.group(5);
            parts.add(new Part(text, before, parameterNo, after, defaultValueGroup != null, defaultValue));
            end = parameterEnd;
        }
        if (end != template.length()) {
            parts.add(new Part(template.substring(end)));
        }
        parts.trimToSize();
        return parts;
    }

    public String render(List<String> parameters) throws ParameterIsNullAndNotHaveDefaultValueException {
        if (parameters == null) {
            parameters = Collections.emptyList();
        }
        IgnoreNullAndRemoveQuotesStringBuilder builder = new IgnoreNullAndRemoveQuotesStringBuilder();
        for (Part part : parts) {
            builder.append(part.getText());
            int parameterNo = part.getParameterNo();
            if (part.isParameterPresent()) {
                String parameterValue = null;
                try {
                    parameterValue = parameters.get(parameterNo);
                } catch (IndexOutOfBoundsException e) {
                    // Ignore
                }
                if (parameterValue != null) {
                    builder.append(part.getBeforeParameterValue())
                            .append(parameterValue)
                            .append(part.getAfterParameterValue());
                } else {
                    if (part.isDefaultValuePresent()) {
                        builder.append(part.getDefaultValue());
                    } else {
                        throw new ParameterIsNullAndNotHaveDefaultValueException(part.getParameterNoInTemplate());
                    }
                }
            }
        }
        return builder.toString();
    }

}
