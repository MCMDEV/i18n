package de.helixdevs.i18n.api;

import java.text.MessageFormat;
import java.util.Locale;

public class TranslationEntry {

    private final Locale locale;
    private final String key;
    private final MessageFormat messageFormat;

    public TranslationEntry(Locale locale, String key, MessageFormat messageFormat) {
        this.locale = locale;
        this.key = key;
        this.messageFormat = messageFormat;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getKey() {
        return key;
    }

    public MessageFormat getMessageFormat() {
        return messageFormat;
    }
}
