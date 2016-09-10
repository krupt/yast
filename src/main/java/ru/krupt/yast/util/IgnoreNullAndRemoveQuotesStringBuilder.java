package ru.krupt.yast.util;

public class IgnoreNullAndRemoveQuotesStringBuilder {

    private final StringBuilder builder = new StringBuilder(128);

    public IgnoreNullAndRemoveQuotesStringBuilder append(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() > 0) {
            builder.append(removeQuotesIfNeeded(charSequence));
        }
        return this;
    }

    private CharSequence removeQuotesIfNeeded(CharSequence charSequence) {
        if (isCharSequenceInQuotes(charSequence)) {
            return charSequence.subSequence(1, charSequence.length() - 1);
        }
        return charSequence;
    }

    private boolean isCharSequenceInQuotes(CharSequence charSequence) {
        return charSequence.charAt(0) == '\''
                && charSequence.charAt(charSequence.length() - 1) == '\'';
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
