package de.helixdevs.i18n.api;

import java.util.Set;

public interface TranslationRepository {

    String getIdentifier();

    Set<TranslationEntry> getTranslations();

}
