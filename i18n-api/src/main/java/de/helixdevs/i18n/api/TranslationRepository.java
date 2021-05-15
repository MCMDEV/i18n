package de.helixdevs.i18n.api;

import java.util.Set;

public interface TranslationRepository {

    Set<TranslationEntry> getTranslations();

}
